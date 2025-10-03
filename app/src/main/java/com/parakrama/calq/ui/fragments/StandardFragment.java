package com.parakrama.calq.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.parakrama.calq.R;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Deque;

public class StandardFragment extends Fragment implements View.OnClickListener {

    private EditText etInput;
    private TextView tvResult;
    private StringBuilder fullExpression = new StringBuilder();
    private boolean justCalculated = false;
    public StandardFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_standard, container, false);

        etInput = view.findViewById(R.id.etInput);
        tvResult = view.findViewById(R.id.tvResult);

        int[] numIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };
        for (int id : numIds) view.findViewById(id).setOnClickListener(this);

        int[] opIds = {
                R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnClear, R.id.btnDelete, R.id.btnEquals
        };
        for (int id : opIds) view.findViewById(id).setOnClickListener(this);

        etInput.setText("");
        tvResult.setText("0");

        return view;
    }

    @Override
    public void onClick(View v) {
        String btnText = ((Button) v).getText().toString();

        if ("×".equals(btnText)) btnText = "*";
        else if ("÷".equals(btnText) || "�÷".equals(btnText)) btnText = "/";
        else if ("−".equals(btnText)) btnText = "-";

        if (justCalculated) {
            if (isNumericOrDot(btnText)) {
                fullExpression.setLength(0);
                justCalculated = false;
            } else if (isOperator(btnText)) {

                justCalculated = false;
            }
        }

        switch (btnText) {
            case "C":
                fullExpression.setLength(0);
                etInput.setText("");
                tvResult.setText("0");
                justCalculated = false;
                break;

            case "⌫":
                if (fullExpression.length() > 0) {
                    fullExpression.deleteCharAt(fullExpression.length() - 1);
                    updateDisplays();
                }
                break;

            case "+":
            case "-":
            case "*":
            case "/":
                appendOperator(btnText.charAt(0));
                break;

            case ".":
                appendDot();
                break;

            case "=":
                onEquals();
                break;

            default:
                if (isNumericOrDot(btnText)) {
                    fullExpression.append(btnText);
                    updateDisplays();
                }
                break;
        }
    }

    private boolean isNumericOrDot(String s) {
        return s.matches("[0-9]") || ".".equals(s);
    }

    private boolean isOperator(String s) {
        return s.length() == 1 && "+-*/".indexOf(s.charAt(0)) >= 0;
    }

    private void appendOperator(char op) {
        if (fullExpression.length() == 0) {
            if (op == '-') {
                fullExpression.append('-');
                updateDisplays();
            }
            return;
        }

        char last = fullExpression.charAt(fullExpression.length() - 1);
        if (isOperatorChar(last)) {
            fullExpression.setCharAt(fullExpression.length() - 1, op);
        } else {
            fullExpression.append(op);
        }
        updateDisplays();
    }

    private boolean isOperatorChar(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private void appendDot() {
        int i = fullExpression.length() - 1;
        while (i >= 0 && !isOperatorChar(fullExpression.charAt(i)) && fullExpression.charAt(i) != '(' && fullExpression.charAt(i) != ')') {
            if (fullExpression.charAt(i) == '.') return;
            i--;
        }
        if (fullExpression.length() == 0 || isOperatorChar(fullExpression.charAt(fullExpression.length() - 1))) {
            fullExpression.append("0.");
        } else {
            fullExpression.append('.');
        }
        updateDisplays();
    }

    private void onEquals() {
        if (fullExpression.length() == 0) return;
        try {
            double res = evaluateExpression(fullExpression.toString());
            String formatted = formatResult(res);
            tvResult.setText(formatted);
            etInput.setText(formatted);
            fullExpression.setLength(0);
            fullExpression.append(formatted);
            justCalculated = true;
        } catch (Exception e) {
            tvResult.setText("Error");
        }
    }

    private void updateDisplays() {
        etInput.setText(prettyDisplay(fullExpression.toString()));

        try {
            if (fullExpression.length() > 0) {
                char last = fullExpression.charAt(fullExpression.length() - 1);
                if (!isOperatorChar(last) && last != '(') {
                    double res = evaluateExpression(fullExpression.toString());
                    tvResult.setText(formatResult(res));
                } else {
                    tvResult.setText(tvResult.getText());
                }
            } else {
                tvResult.setText("0");
            }
        } catch (Exception ex) {
            tvResult.setText("...");
        }
    }

    private String prettyDisplay(String expr) {
        if (TextUtils.isEmpty(expr)) return "";
        return expr.replace("*", "×").replace("/", "÷");
    }

    private String formatResult(double val) {
        if (Double.isNaN(val) || Double.isInfinite(val)) return "Error";
        if (Math.abs(val - Math.round(val)) < 1e-12) {
            return String.valueOf(Math.round(val));
        } else {
            DecimalFormat df = new DecimalFormat("#.########");
            return df.format(val);
        }
    }

    private double evaluateExpression(String expr) throws Exception {
        expr = expr.replaceAll("\\s+", "");
        expr = handleUnaryMinus(expr);

        Deque<Double> values = new ArrayDeque<>();
        Deque<Character> ops = new ArrayDeque<>();

        int i = 0;
        int n = expr.length();
        while (i < n) {
            char c = expr.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                int j = i;
                while (j < n && (Character.isDigit(expr.charAt(j)) || expr.charAt(j) == '.')) j++;
                String numStr = expr.substring(i, j);
                values.push(Double.parseDouble(numStr));
                i = j;
                continue;
            } else if (c == '(') {
                ops.push(c);
            } else if (c == ')') {
                while (!ops.isEmpty() && ops.peek() != '(') {
                    applyTopOp(values, ops);
                }
                if (ops.isEmpty()) throw new Exception("Mismatched parentheses");
                ops.pop();
            } else if (isOperatorChar(c)) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(c)) {
                    applyTopOp(values, ops);
                }
                ops.push(c);
            } else {
                throw new Exception("Invalid char: " + c);
            }
            i++;
        }

        while (!ops.isEmpty()) {
            if (ops.peek() == '(' || ops.peek() == ')') throw new Exception("Mismatched parentheses");
            applyTopOp(values, ops);
        }

        if (values.isEmpty()) throw new Exception("Invalid expression");
        return values.pop();
    }

    private String handleUnaryMinus(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '-') {
                if (i == 0) {
                    sb.append("0-");
                } else {
                    char prev = s.charAt(i - 1);
                    if (prev == '(' || isOperatorChar(prev)) {
                        sb.append("0-");
                    } else {
                        sb.append('-');
                    }
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void applyTopOp(Deque<Double> values, Deque<Character> ops) throws Exception {
        if (values.size() < 2) throw new Exception("Invalid expression");
        double b = values.pop();
        double a = values.pop();
        char op = ops.pop();
        double res;
        switch (op) {
            case '+': res = a + b; break;
            case '-': res = a - b; break;
            case '*': res = a * b; break;
            case '/':
                if (b == 0) throw new Exception("Division by zero");
                res = a / b; break;
            default:
                throw new Exception("Unknown op: " + op);
        }
        values.push(res);
    }

    private int precedence(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
        }
        return 0;
    }
}
