package com.vittorioprivitera.calcolatrice;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button bZero_btn,b1_btn,b2_btn,b3_btn,b4_btn,b5_btn,
            b6_btn,b7_btn,b8_btn,b9_btn;
    Button bPiu_btn,bMeno_btn,bPer_btn,bDiv_btn,bAllc;
    EditText lOperazione;
    TextView lRisultato;

    float num1,num2,ris;
    String operazione,testoOper;

    private void inserisciCifra(String cifra) {
        int cursore=lOperazione.getSelectionStart();
        String testo=lOperazione.getText().toString();
        StringBuilder sb=new StringBuilder(testo);
        sb.insert(cursore,cifra);
        lOperazione.setText(sb.toString());
        //Sposta il cursore in avanti di una posizione cosi posso aggiungere
        lOperazione.setSelection(cursore+cifra.length());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bPiu_btn=findViewById(R.id.BPiu_btn);
        bMeno_btn=findViewById(R.id.bMeno_btn);
        bPer_btn=findViewById(R.id.bPer_btn);
        bDiv_btn=findViewById(R.id.bDiviso_btn);
        //bZero_btn=findViewById(R.id.b0_btn);
        lOperazione=findViewById(R.id.lOperazione_txt);
        lRisultato=findViewById(R.id.lrisultato_txt);
        bAllc=findViewById(R.id.BCanc_btn);


        bAllc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num1=0;
                num2=0;
                ris=0;
                operazione="";
                lOperazione.setText("");
                lRisultato.setText("");
                testoOper="";
            }
        });


        b1_btn=findViewById(R.id.b1_btn);
        b1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("1");
            }
        });
        b2_btn=findViewById(R.id.b2_btn);
        b2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("2");
            }
        });
        b3_btn=findViewById(R.id.b3_btn);
        b3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("3");
            }
        });
        b4_btn=findViewById(R.id.b4_btn);
        b4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("4");
            }
        });
        b5_btn=findViewById(R.id.b5_btn);
        b5_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("5");
            }
        });
        b6_btn=findViewById(R.id.b6_btn);
        b6_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("6");
            }
        });
        b7_btn=findViewById(R.id.b7_btn);
        b7_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("7");
            }
        });
        b8_btn=findViewById(R.id.b8_btn);
        b8_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("8");
            }
        });
        b9_btn=findViewById(R.id.b9_btn);
        b9_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("9");
            }
        });
    }
}