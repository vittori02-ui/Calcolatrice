package com.vittorioprivitera.calcolatrice;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button bZero_btn,b1_btn,b2_btn,b3_btn,b4_btn,b5_btn,
            b6_btn,b7_btn,b8_btn,b9_btn;
    Button bPiu_btn,bMeno_btn,bPer_btn,bDiv_btn,bAllc,bUgual,calcSci,bVirg,
            blog,bPotenza,bPerc,bCanc,bRad2,bSeconda,bRad3,bFatt,bSeno,bCosn;
    EditText lOperazione;
    TextView lRisultato;
    LinearLayout layer0,layer1;
    float num1,num2,ris;
    String operazione,testoOper;
    private void cancellaCifra()
    {
        int cursore=lOperazione.getSelectionStart();
        String testo=lOperazione.getText().toString();
        if(testo.isEmpty()) return;
        if(cursore==0)cursore=testo.length();
        StringBuilder sb=new StringBuilder(testo);
        sb.deleteCharAt(cursore-1);
        lOperazione.setText(sb.toString());
        lOperazione.setSelection(cursore-1);//rimetto il cursore dove era prima
        int cur=lOperazione.getText().toString().indexOf(operazione);
        if(cur==-1)
        {
            operazione="";
        }
        System.out.println(operazione);
    }

    private void gestioneOpe2(String ope)
    {
        if(!lRisultato.getText().toString().equals("")&&!operazione.isEmpty())
        {
            num1=Float.parseFloat(lRisultato.getText().toString());
            lOperazione.setText(formattaRis(num1)+ope);
            lOperazione.setSelection(lOperazione.getText().toString().length());
            operazione=ope;
        }
        else
        {
            inserisciCifra(ope);
            operazione=ope;
        }
    }
    private String formattaRis(float valore)
    {
        if(valore==(long)valore) return String.format("%d",(long)valore);
        else
        {
            String s=String.format("%.6f",valore).replace(",",".");
            if(s.contains("."))s=s.replaceAll("0*$", "").replaceAll("\\.$", "");//sono dei simboli standard per formattare togliendo le due cifre
            return s;
        }
    }
    private void gestioneOpe(String ope) {
        String testo=lOperazione.getText().toString();
        if(ope.equals("-"))
        {
            if (!operazione.isEmpty() && (testo.endsWith("x") || testo.endsWith("÷"))) {
                inserisciCifra("-");
                return;
            }
            if(testo.isEmpty())
            {
                inserisciCifra("-");
                return;
            }
        }
        if(testo.isEmpty()||testo.endsWith("-")||testo.endsWith("+")) return;
        if(!operazione.isEmpty()) {
            if (testo.endsWith(operazione)) {
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
        inserisciCifra(ope);
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
    //in modo tale da avere l'ordine giusto
    private ArrayList<String> creaOpe(String espressione)
    {
        String split="(?<=[-+x÷^√∛!]|sen|cos|log|%)|(?=[-+x÷^√∛!]|sen|cos|log|%)"; //il regex
        String[] array=espressione.split(split);
        return new ArrayList<>(Arrays.asList(array));
    }
    private float calcoloTotale(String espre)
    {
        ArrayList<String> ope = creaOpe(espre);
        System.out.println(ope);
        float risParz;
        int i=0;
        if(ope.size()>1&&ope.get(0).equals("-"))
        {
            String numeroNeg="-"+ope.get(1);
            ope.set(0,numeroNeg);
            ope.remove(1); //rimuovo il vecchio -
            System.out.println(ope);
        }
        else if(!ope.isEmpty()&&ope.get(0).isEmpty())ope.remove(0);
        //primo passagio per le operazioni a una direzione
        System.out.println(ope);
        float valore;
        while(i<ope.size())
        {
            risParz=0;
            String t=ope.get(i);
            if(t.equals("sen")||t.equals("cos")||t.equals("log")||t.equals("√")||t.equals("∛"))
            {
                valore=Float.parseFloat(ope.get(i+1));
                if(t.equals("sen"))risParz=(float)Math.sin(Math.toRadians(valore));
                else if(t.equals("√"))risParz=(float)Math.sqrt(valore);
                else if(t.equals("∛"))risParz=(float)Math.cbrt(valore);
                else if(t.equals("cos"))risParz=(float)Math.cos(Math.toRadians(valore));
                else if(t.equals("log"))risParz=(float)Math.log10(valore);
                ope.set(i,String.valueOf(risParz));
                ope.remove(i+1);
                System.out.println(ope);
            }
            else if(t.equals("!"))
            {
                risParz=1;
                for(int j=1;j<=Float.parseFloat(ope.get(i-1));j++)
                {
                    risParz=risParz*j;
                }
                ope.set(i-1,String.valueOf(risParz));
                ope.remove(i);
                System.out.println(ope);
            }
            else if(t.equals("%"))
            {
                float a=Float.parseFloat(ope.get(i-1));
                if(i+1<ope.size()&&numero(ope.get(i+1)))
                {
                    float b=Float.parseFloat(ope.get(i+1));
                    risParz=(a/100)*b;
                    ope.set(i-1,String.valueOf(risParz));
                    ope.remove(i); //rimuove l'operazione
                    ope.remove(i); //rimuove il secondo numero
                }
                else
                {
                    risParz=a/100;
                    ope.set(i-1,String.valueOf(risParz));
                    ope.remove(i);
                }
                System.out.println(ope);
            }
            else i++;
        }
        //secondo passaggio
        i=0;
        while(i<ope.size())
        {
            String t=ope.get(i);
            if(t.equals("^")||t.equals("^2"))
            {
                float a=Float.parseFloat(ope.get(i-1));
                if(t.equals("^"))
                {
                    float b=Float.parseFloat(ope.get(i+1));
                    risParz=(float) Math.pow(a,b);
                    ope.set(i-1,String.valueOf(risParz));
                    ope.remove(i); //rimuove ^
                    ope.remove(i); //rimuove l'esponente
                } else
                {
                    risParz=(float)Math.pow(a,2);
                    ope.set(i-1,String.valueOf(risParz));
                    ope.remove(i); //rimuove^2
                }
                System.out.println(ope);
            }
            else i++;
        }
        while(i<ope.size()) {
            String t=ope.get(i);
            if (t.equals("x")||t.equals("÷")) {
                float a=Float.parseFloat(ope.get(i-1));
                float b=Float.parseFloat(ope.get(i+1));
                if (t.equals("x"))risParz=a*b;
                else if(t.equals("÷")) risParz=a/b;
                ope.remove(i); // rimuove l'operatore
                ope.remove(i); // rimuove il secondo numero
                System.out.println(ope);
            }
            else i++;
        }
        i=0;
        while(i<ope.size())
        {
            String t=ope.get(i);
            if (t.equals("+")||t.equals("-"))
            {
                float a=Float.parseFloat(ope.get(i-1));
                float b=Float.parseFloat(ope.get(i+1));
                if(t.equals("+"))risParz=a+b;
                else if(numero(ope.get(i-1)))risParz=a-b;
                System.out.println(ope);
            }
            else i++;
        }
        return Float.parseFloat(ope.get(0));
    }
    private boolean numero(String s)
    {
        try
        {
            Float.parseFloat(s);
            return true;
        } catch (Exception e)
        {
            return false;
        }
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
        bCanc=findViewById(R.id.BCanc_btn);
        bRad2=findViewById(R.id.bRad2_btn);
        bSeno=findViewById(R.id.bSen_btn);
        bCosn=findViewById(R.id.bCos_btn);
        bSeconda=findViewById(R.id.besp2_btn);
        bRad3=findViewById(R.id.bRad3_btn);
        bFatt=findViewById(R.id.bFatto_btn);
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

        bSeconda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe("^2");
            }
        });

        bRad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe2("√");
            }
        });

        bRad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe2("∛");
            }
        });

        bFatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe("!");
            }
        });

        bSeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe2("sen");
            }
        });

        bCosn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe2("cos");
            }
        });

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe2("log");
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
        bCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancellaCifra();
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

        bRad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestioneOpe2("√");
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

        bZero_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciCifra("0");
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