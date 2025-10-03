package com.parakrama.calq.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.fragment.app.Fragment;
import com.parakrama.calq.R;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

public class ScientificFragment extends Fragment {

    private EditText etInput;
    private TextView tvResult;
    private StringBuilder input = new StringBuilder();
    private boolean isDegree = true;
    private double lastAnswer = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scientific, container, false);

        etInput = view.findViewById(R.id.etInput);
        tvResult = view.findViewById(R.id.tvResult);

        int[] numIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot};

        View.OnClickListener numClick = v -> {
            Button b = (Button) v;
            input.append(b.getText());
            etInput.setText(input.toString());
        };
        for (int id : numIds) view.findViewById(id).setOnClickListener(numClick);

        int[] parenIds = {R.id.btnOpen, R.id.btnClose};
        View.OnClickListener parenClick = v -> {
            Button b = (Button) v;
            input.append(b.getText());
            etInput.setText(input.toString());
        };
        for (int id : parenIds) view.findViewById(id).setOnClickListener(parenClick);

        int[] opIds = {R.id.btnAdd, R.id.btnSub, R.id.btnMul, R.id.btnDiv,
                R.id.btnPow, R.id.btnSin, R.id.btnCos, R.id.btnTan,
                R.id.btnLog, R.id.btnExp, R.id.btnSqrt, R.id.btnPi,
                R.id.btnAns, R.id.btnDel};

        View.OnClickListener opClick = v -> {
            Button b = (Button) v;
            String text = b.getText().toString();

            switch (text) {
                case "sin": input.append("sin("); break;
                case "cos": input.append("cos("); break;
                case "tan": input.append("tan("); break;
                case "log": input.append("log10("); break;
                case "exp": input.append("exp("); break;
                case "√": input.append("sqrt("); break;
                case "π": input.append(Math.PI); break;
                case "xʸ": input.append("^"); break;
                case "ANS": input.append(lastAnswer); break;
                case "DEL":
                    if (input.length() > 0) input.deleteCharAt(input.length() - 1);
                    break;
                case "×": input.append("*"); break;
                case "÷": input.append("/"); break;
                default: input.append(text); break;
            }
            etInput.setText(input.toString());
        };
        for (int id : opIds) view.findViewById(id).setOnClickListener(opClick);

        view.findViewById(R.id.btnClear).setOnClickListener(v -> {
            input.setLength(0);
            etInput.setText("");
            tvResult.setText("");
        });

        view.findViewById(R.id.btnEqual).setOnClickListener(v -> calculateResult());

        ToggleButton btnDegRad = view.findViewById(R.id.btnDegRad);
        btnDegRad.setOnClickListener(v -> isDegree = btnDegRad.isChecked());

        return view;
    }

    private void calculateResult() {
        try {
            Expression expr = new ExpressionBuilder(input.toString())
                    .functions(
                            new Function("sin", 1) {
                                @Override
                                public double apply(double... args) {
                                    return Math.sin(isDegree ? Math.toRadians(args[0]) : args[0]);
                                }
                            },
                            new Function("cos", 1) {
                                @Override
                                public double apply(double... args) {
                                    return Math.cos(isDegree ? Math.toRadians(args[0]) : args[0]);
                                }
                            },
                            new Function("tan", 1) {
                                @Override
                                public double apply(double... args) {
                                    return Math.tan(isDegree ? Math.toRadians(args[0]) : args[0]);
                                }
                            },
                            new Function("log10", 1) {
                                @Override
                                public double apply(double... args) {
                                    return Math.log10(args[0]);
                                }
                            },
                            new Function("exp", 1) {
                                @Override
                                public double apply(double... args) {
                                    return Math.exp(args[0]);
                                }
                            },
                            new Function("sqrt", 1) {
                                @Override
                                public double apply(double... args) {
                                    return Math.sqrt(args[0]);
                                }
                            }
                    ).build();

            double result = expr.evaluate();
            lastAnswer = result;
            tvResult.setText(formatResult(result));
        } catch (Exception e) {
            tvResult.setText("Syntax Error");
            e.printStackTrace();
        }
    }

    private String formatResult(double res) {
        if (res == (long) res) return String.valueOf((long) res);
        return String.format("%.6f", res);
    }
}
