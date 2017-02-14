package com.invert.ifyou.textinvert;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vdurmont.emoji.Emoji;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.Fitzpatrick;

public class MainActivity extends AppCompatActivity {

    EditTextEx iNorm;
    TextView iInvert;
    TextView iBack;
    ImageView iInvertCopy;
    ImageView iBackCopy;
    ImageView iAbout;
    CardView invert;
    CardView back;
    LinearLayout full;
    int First;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        First = 0;
        invert = (CardView) findViewById(R.id.card_view);
        back = (CardView) findViewById(R.id.card_view2);
        invert.setCardBackgroundColor(Color.WHITE);
        back.setCardBackgroundColor(Color.WHITE);
        full = (LinearLayout)findViewById(R.id.full) ;

        iNorm = (EditTextEx) findViewById(R.id.normText);
        //iNorm.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY));
        TextWatcherP inputTextWatcher = new TextWatcherP(iNorm);
        iNorm.addTextChangedListener(inputTextWatcher);
        iNorm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getSupportActionBar().show();
                    getWindow().getDecorView().clearFocus();
                    return true;
                }
                return false;
            }
        });
        iNorm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getSupportActionBar().hide();
                    if (First == 0)
                        iNorm.setText("");
                    First = 1;
                    invert.setCardBackgroundColor(Color.GRAY);
                    back.setCardBackgroundColor(Color.GRAY);
                    full.setBackgroundColor(Color.GRAY);
                } else {
                    getSupportActionBar().show();
                    invert.setCardBackgroundColor(Color.WHITE);
                    back.setCardBackgroundColor(Color.WHITE);
                    full.setBackgroundColor(Color.WHITE);
                }
            }
        });
        iNorm.setOnKeyListener(new TextView.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getSupportActionBar().show();
                    getWindow().getDecorView().clearFocus();
                    invert.setCardBackgroundColor(Color.WHITE);
                    back.setCardBackgroundColor(Color.WHITE);
                    full.setBackgroundColor(Color.WHITE);
                    return true;
                }
                return false;
            }
        });
        iInvert = (TextView) findViewById(R.id.invertText);
        iBack = (TextView) findViewById(R.id.backText);

        iAbout = (ImageView) findViewById(R.id.imageView0);
        iAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Baranov Artem\nifyousleep@gmail.com\n2015\nʞodıןɟ");
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        iInvertCopy = (ImageView) findViewById(R.id.imageView1);
        iInvertCopy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Copy(iInvert.getText().toString());
                Snackbar snackbar;
                if (!iInvert.getText().toString().isEmpty()) {
                    snackbar = Snackbar
                            .make(arg0, "Successfully copied!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    snackbar = Snackbar
                            .make(arg0, "Empty!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                return true;
            }
        });
        iBackCopy = (ImageView) findViewById(R.id.imageView2);
        iBackCopy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                Copy(iBack.getText().toString());
                Snackbar snackbar;
                if (!iBack.getText().toString().isEmpty()) {
                    snackbar = Snackbar
                            .make(arg0, "Successfully copied!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    snackbar = Snackbar
                            .make(arg0, "Empty!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                return true;
            }
        });
    }

    private void Copy(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(text);
    }

    private boolean validateName(String et) {
        et = removeEmojis(et);
        String[] ary = new StringBuffer(et).reverse().toString().split("");
        iInvert.setText("");
        iBack.setText("");
        if (!et.isEmpty()) {
            for (int x = 0; x < et.length(); x = x + 1) {
                //if (et.length() > x+1)
                iInvert.append(cOnvert(ary[x + 1]));
            }
            iBack.append(new StringBuffer(et).reverse().toString());
        } else
            iInvert.append("");
        return true;
    }

    private String removeEmojis(String str) {
        //remove all fitzpatrick modifiers
        for (Fitzpatrick fitzpatrick : Fitzpatrick.values()) {
            str = str.replaceAll(fitzpatrick.unicode, "");
        }
        //remove all emojis
        for (Emoji emoji : EmojiManager.getAll()) {
            str = str.replaceAll(emoji.getUnicode(), "");
        }

        return str;
    }

    private String cOnvert(String s) {
        char c = s.toLowerCase().charAt(s.length() - 1);
        if (c == 'a') {
            return "" + '\u0250';
        } else if (c == 'b') {
            return "" + 'q';
        } else if (c == 'c') {
            return "" + '\u0254';
        } else if (c == 'd') {
            return "" + 'p';
        } else if (c == 'e') {
            return "" + '\u01DD';
        } else if (c == 'f') {
            return "" + '\u025F';
        } else if (c == 'g') {
            return "" + 'b';
        } else if (c == 'h') {
            return "" + '\u0265';
        } else if (c == 'i') {
            return "" + '\u0131'; //""+'\u0131\u0323'
        } else if (c == 'j') {
            return "" + '\u0638';
        } else if (c == 'k') {
            return "" + '\u029E';
        } else if (c == 'l') {
            return "" + '\u05DF';
        } else if (c == 'm') {
            return "" + '\u026F';
        } else if (c == 'n') {
            return "" + 'u';
        } else if (c == 'o') {
            return "" + 'o';
        } else if (c == 'p') {
            return "" + 'd';
        } else if (c == 'q') {
            return "" + 'b';
        } else if (c == 'r') {
            return "" + '\u0279';
        } else if (c == 's') {
            return "" + 's';
        } else if (c == 't') {
            return "" + '\u0287';
        } else if (c == 'u') {
            return "" + 'n';
        } else if (c == 'v') {
            return "" + '\u028C';
        } else if (c == 'w') {
            return "" + '\u028D';
        } else if (c == 'x') {
            return "" + 'x';
        } else if (c == 'y') {
            return "" + '\u028E';
        } else if (c == 'z') {
            return "" + 'z';
        } else if (c == '[') {
            return "" + ']';
        } else if (c == ']') {
            return "" + '[';
        } else if (c == '(') {
            return "" + ')';
        } else if (c == ')') {
            return "" + '(';
        } else if (c == '{') {
            return "" + '}';
        } else if (c == '}') {
            return "" + '{';
        } else if (c == '?') {
            return "" + '\u00BF';
        } else if (c == '\u00BF') {
            return "" + '?';
        } else if (c == '!') {
            return "" + '\u00A1';
        } else if (c == ',') {
            return "" + "\'";
        } else if (c == '.') {
            return "" + '\u02D9';
        } else if (c == '_') {
            return "" + '\u203E';
        } else if (c == ';') {
            return "" + '\u061B';
        } else if (c == '9') {
            return "" + '6';
        } else if (c == '6') {
            return "" + '9';
        } else if (c == '1') {
            return "" + '\u0196';
        } else if (c == '2') {
            return "" + '\u1105';
        } else if (c == '3') {
            return "" + '\u0190';
        } else if (c == '4') {
            return "" + '\u3123';
        } else if (c == '5') {
            return "" + '\u03DB';
        } else if (c == '7') {
            return "" + '\u3125';
        } else if (c == '8') {
            return "" + '8';
        }
        if (c == 'а') {
            return "" + 'ɐ';
        } else if (c == 'б') {
            return "" + 'ƍ';
        } else if (c == 'в') {
            return "" + 'ʚ';
        } else if (c == 'г') {
            return "" + 'ɹ';
        } else if (c == 'д') {
            return "" + 'ɓ';
        } else if (c == 'е') {
            return "" + 'ǝ';
        } else if (c == 'ё') {
            return "" + 'ǝ';
        } else if (c == 'ж') {
            return "" + 'ж';
        } else if (c == 'з') {
            return "" + 'ε';
        } else if (c == 'и') {
            return "" + 'и';
        } else if (c == 'й') {
            return "" + 'ņ';
        } else if (c == 'к') {
            return "" + 'ʞ';
        } else if (c == 'л') {
            return "" + 'v';
        } else if (c == 'м') {
            return "" + 'w';
        } else if (c == 'н') {
            return "" + 'н';
        } else if (c == 'о') {
            return "" + 'о';
        } else if (c == 'п') {
            return "" + 'u';
        } else if (c == 'р') {
            return "" + 'd';
        } else if (c == 'с') {
            return "" + 'ɔ';
        } else if (c == 'т') {
            return "" + 'ɯ';
        } else if (c == 'у') {
            return "" + 'ʎ';
        } else if (c == 'ф') {
            return "" + 'ȸ';
        } else if (c == 'х') {
            return "" + 'х';
        } else if (c == 'ц') {
            return "" + 'ǹ';
        } else if (c == 'ч') {
            return "" + 'Һ';
        } else if (c == 'ш') {
            return "" + 'm';
        } else if (c == 'щ') {
            return "" + 'm';
        } else if (c == 'ъ') {
            return "" + 'q';
        } else if (c == 'ы') {
            return "" + 'ı' + 'q';
        } else if (c == 'ь') {
            return "" + 'q';
        } else if (c == 'э') {
            return "" + 'є';
        } else if (c == 'ю') {
            return "" + 'o' + 'ı';
        } else if (c == 'я') {
            return "" + 'ʁ';
        }
        //else c = ' ';
        return "" + c;
    }

    public class TextWatcherP implements TextWatcher {

        public EditTextEx editText;

        public TextWatcherP(EditTextEx et) {
            super();
            editText = et;
        }

        public void afterTextChanged(Editable s) {
            validateName(editText.getText().toString());
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
    }
}
