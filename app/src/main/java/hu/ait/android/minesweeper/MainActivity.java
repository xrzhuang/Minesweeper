package hu.ait.android.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private static ToggleButton toggleFlagBtn;
    private static Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final MinesweeperView minesweeperView = findViewById(R.id.gameboard);
        minesweeperView.start();
        clearBtn = findViewById(R.id.clearButton);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minesweeperView.restart();
                MinesweeperModel.GAMESTATE = 0;

            }
        });


        toggleFlagBtn = findViewById(R.id.toggleFlagBtn);
        toggleFlagBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MinesweeperModel.FLAGMODE = true;
                }
                else {
                    MinesweeperModel.FLAGMODE = false;
                }
            }
        });

    }
}
