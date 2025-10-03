package com.parakrama.calq.ui.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.parakrama.calq.R;

public class ProgrammerFragment extends Fragment {

    private EditText etDisplay;
    private TextView tvBin, tvOct, tvDec, tvHex;
    private int currentBase = 10;
    private String input = "";
    private String pendingOperation = "";
    private int operand1 = 0;

    public ProgrammerFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_programmer, container, false);

        etDisplay = view.findViewById(R.id.etDisplay);
        tvBin = view.findViewById(R.id.tvBin);
        tvOct = view.findViewById(R.id.tvOct);
        tvDec = view.findViewById(R.id.tvDec);
        tvHex = view.findViewById(R.id.tvHex);

        view.findViewById(R.id.btnBin).setOnClickListener(v -> selectBase(2));
        view.findViewById(R.id.btnOct).setOnClickListener(v -> selectBase(8));
        view.findViewById(R.id.btnDec).setOnClickListener(v -> selectBase(10));
        view.findViewById(R.id.btnHex).setOnClickListener(v -> selectBase(16));

        int[] numIds = {R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9,
                R.id.btnA,R.id.btnB,R.id.btnC,R.id.btnD,R.id.btnE,R.id.btnF};

        for(int id : numIds){
            view.findViewById(id).setOnClickListener(v -> {
                Button b = (Button)v;
                String value = b.getText().toString();
                if(isValidForBase(value, currentBase)){
                    input += value;
                    etDisplay.setText(input);
                    updateConversions();
                } else {
                    Toast.makeText(getContext(), "Invalid input for this base", Toast.LENGTH_SHORT).show();
                }
            });
        }

        view.findViewById(R.id.btnAND).setOnClickListener(v -> setOperation("AND"));
        view.findViewById(R.id.btnOR).setOnClickListener(v -> setOperation("OR"));
        view.findViewById(R.id.btnXOR).setOnClickListener(v -> setOperation("XOR"));
        view.findViewById(R.id.btnNOT).setOnClickListener(v -> applyNot());

        view.findViewById(R.id.btnClear).setOnClickListener(v -> {
            input = "";
            pendingOperation = "";
            etDisplay.setText("0");
            updateConversions();
        });

        view.findViewById(R.id.btnBack).setOnClickListener(v -> {
            if(!TextUtils.isEmpty(input)){
                input = input.substring(0, input.length()-1);
                etDisplay.setText(input.isEmpty() ? "0" : input);
                updateConversions();
            }
        });

        view.findViewById(R.id.btnEquals).setOnClickListener(v -> computeOperation());

        return view;
    }

    private void selectBase(int base){
        currentBase = base;
        Toast.makeText(getContext(), "Base: " + base, Toast.LENGTH_SHORT).show();
        input = "";
        etDisplay.setText("0");
        updateConversions();
    }

    private boolean isValidForBase(String value, int base){
        try{
            Integer.parseInt(value, base);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private void setOperation(String op){
        if(TextUtils.isEmpty(input)) return;
        try{
            operand1 = Integer.parseInt(input, currentBase);
            pendingOperation = op;
            input = "";
            etDisplay.setText("0");
        } catch (Exception e){ Toast.makeText(getContext(), "Invalid number", Toast.LENGTH_SHORT).show(); }
    }

    private void applyNot(){
        if(TextUtils.isEmpty(input)) return;
        try{
            int val = Integer.parseInt(input, currentBase);
            val = ~val;
            input = Integer.toString(val, currentBase).toUpperCase();
            etDisplay.setText(input);
            updateConversions();
        } catch (Exception e){ Toast.makeText(getContext(), "Invalid input", Toast.LENGTH_SHORT).show(); }
    }

    private void computeOperation(){
        if(TextUtils.isEmpty(input) || TextUtils.isEmpty(pendingOperation)) return;
        try{
            int operand2 = Integer.parseInt(input, currentBase);
            int result = 0;

            switch (pendingOperation){
                case "AND": result = operand1 & operand2; break;
                case "OR":  result = operand1 | operand2; break;
                case "XOR": result = operand1 ^ operand2; break;
            }

            input = Integer.toString(result, currentBase).toUpperCase();
            etDisplay.setText(input);
            updateConversions();
            pendingOperation = "";
        } catch (Exception e){ Toast.makeText(getContext(), "Error computing", Toast.LENGTH_SHORT).show(); }
    }

    private void updateConversions(){
        if(TextUtils.isEmpty(input)) return;
        try{
            int val = Integer.parseInt(input, currentBase);
            tvBin.setText("BIN: "+Integer.toBinaryString(val));
            tvOct.setText("OCT: "+Integer.toOctalString(val));
            tvDec.setText("DEC: "+val);
            tvHex.setText("HEX: "+Integer.toHexString(val).toUpperCase());
        } catch (Exception e){ }
    }
}
