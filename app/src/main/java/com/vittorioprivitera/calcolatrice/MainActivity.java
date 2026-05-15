package com.vittorioprivitera.calcolatrice;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;
public class MainActivity extends AppCompatActivity {
    Button bZero_btn,b1_btn,b2_btn,b3_btn,b4_btn,b5_btn,
            b6_btn,b7_btn,b8_btn,b9_btn;
    Button bPiu_btn,bMeno_btn,bPer_btn,bDiv_btn,bAllc,bUgual,calcSci,bVirg,
            blog,bPotenza,bPerc;
    EditText lOperazione;
    TextView lRisultato;
    LinearLayout layer0,layer1;
    float num1,num2,ris;
    String operazione,testoOper;
    private String formattaRis(float valore)
    {
        if(valore==(long)valore) return String.format("%d",(long)valore);
        else
        {
            String s=String.format("%.6f",valore).replace(",",".");
            if(s.contains("."))s=s.replaceAll("0*$", "").replaceAll("\\.$", "");
            return s;
        }
    }
    private void gestioneOpe(String ope) {
        String testo=lOperazione.getText().toString();
        if(ope.equals("-")) {
            if (!operazione.isEmpty() && (testo.endsWith("x") || testo.endsWith("÷"))) {
                inserisciCifra("-");
                return;
            }

        }
        if(!operazione.isEmpty()) {
            if (testo.endsWith(operazione) && !testo.endsWith("!") && !testo.endsWith("%")) {
                testo = testo.substring(0, testo.length() - 1);
                lOperazione.setText(testo + ope);
                lOperazione.setSelection(lOperazione.getText().length());
                operazione = ope;
                return;
            }
            if (!lRisultato.getText().toString().isEmpty()) {
                num1 = Float.parseFloat(lRisultato.getText().toString());
                lOperazione.setText(formattaRis(num1) + ope);
                lOperazione.setSelection(lOperazione.getText().toString().length());
                lRisultato.setText("");
                operazione = ope;
                return;
            }
        }
        operazione=ope;
    }


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
        bUgual=findViewById(R.id.bUguale_btn);
        bVirg=findViewById(R.id.bVirg_btn);
        layer1=findViewById(R.id.linear1);
        layer0=findViewById(R.id.layer0);
        blog=findViewById(R.id.bLog_btn);
        bPotenza=findViewById(R.id.bPotenza_btn);
        bPerc=findViewById(R.id.bPer_btn);

        calcSci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layer1.getVisibility()==View.VISIBLE)
                {
                    layer1.setVisibility(View.GONE);
                    layer0.setVisibility(View.GONE);
                } else
                {
                    layer1.setVisibility(View.VISIBLE);
                    layer0.setVisibility(View.VISIBLE);
                }
            }
        });
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gestioneOpe("log"); non funziona
            }
        });

        bPotenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe("^");
            }
        });

        bPerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe("%");
            }
        });
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

        bVirg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testo=lOperazione.getText().toString();
                int posOp=-1;
                if(!operazione.isEmpty()) posOp=testo.indexOf(operazione);
                String parteAttuale;
                if(posOp==-1)parteAttuale=testo;// Non c'è operatore, stiamo scrivendo num1
                else parteAttuale=testo.substring(posOp + 1);// C'è l'operatore, stiamo scrivendo num2
                if(!parteAttuale.contains(".")) {
                    if (parteAttuale.isEmpty()||parteAttuale.equals("-"))inserisciCifra("0.");
                    else inserisciCifra(".");
                }
            }
        });

        bUgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testoOper=lOperazione.getText().toString();
                try
                {
                    num2=Float.parseFloat(lOperazione.getText().toString());
                    switch(operazione)
                    {
                        case "+":
                            ris=num1+num2;
                            break;
                        case "-":
                            ris=num1-num2;
                            break;
                        case "*":
                            ris=num1*num2;
                            break;
                        case "/":
                            if(num2==0)return;
                            ris=num1/num2;
                            break;
                    }
                    lRisultato.setText(formattaRis(ris));

                } catch (Exception e)
                {

                }
            }
        });

        bPiu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lRisultato.getText().toString().isEmpty())return;
                else
                {
                    num1=Integer.parseInt(lRisultato.getText().toString());
                    testoOper=num1+"+";
                    lRisultato.setText("");
                    lOperazione.setText("");
                    lOperazione.append(testoOper);
                    operazione="+";
                }
            }
        });

        bMeno_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lRisultato.getText().toString().isEmpty())return;
                else
                {
                    num1=Integer.parseInt(lRisultato.getText().toString());
                    testoOper=num1+"-";
                    lRisultato.setText("");
                    lOperazione.setText("");
                    lOperazione.append(testoOper);
                    operazione="-";
                }
            }
        });

        bPer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lRisultato.getText().toString().isEmpty())return;
                else
                {
                    num1=Integer.parseInt(lRisultato.getText().toString());
                    testoOper=num1+"x";
                    lRisultato.setText("");
                    lOperazione.setText("");
                    lOperazione.append(testoOper);
                    operazione="x";
                }
            }
        });

        bDiv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lRisultato.getText().toString().isEmpty())return;
                else
                {
                    num1=Integer.parseInt(lRisultato.getText().toString());
                    testoOper=num1+"/";
                    lRisultato.setText("");
                    lOperazione.setText("");
                    lOperazione.append(testoOper);
                    operazione="/";
                }
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