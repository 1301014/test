package com.example.dentaku2;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivityDentaku extends Activity implements View.OnClickListener{
    /**
 * Buttonの配列
 */
Button mButton[];

/**
 * Idの配列
 */
int mId[] = { R.id.button0, R.id.button1, R.id.button2, R.id.button3,
		R.id.button4, R.id.button5, R.id.button6, R.id.button7,
		R.id.button8, R.id.button9, R.id.buttonPlus, R.id.buttonMinus,
		R.id.buttonEqual, R.id.buttonTen, R.id.buttonClear,R.id.waru,R.id.kake,
		};

/**
 * キー
 */
private final int KEY_0 = 0;
private final int KEY_1 = 1;
private final int KEY_2 = 2;
private final int KEY_3 = 3;
private final int KEY_4 = 4;
private final int KEY_5 = 5;
private final int KEY_6 = 6;
private final int KEY_7 = 7;
private final int KEY_8 = 8;
private final int KEY_9 = 9;
private final int KEY_PLUS = 10;
private final int KEY_MINUS = 11;
private final int KEY_EQUAL = 12;
private final int KEY_TEN = 13;
private final int KEY_CLEAR = 14;
private final int KEY_waru = 15;
private final int KEY_kake = 16;

/**
 * TextView
 */
TextView mTextView;

/**
 * 前の処理
 */
int beforeStatus = 0;

/**
 * 合計
 */
int total = 0;

     /**
 	 * 計算中の値 
 	 */ 
 	private ArrayList<String> calcArray; 
 	/** 
 	 * 計算する時の配列 
 	 */ 
	private ArrayList<String> signArray; 



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentaku);
        
        Button kakeibobtn3 = (Button)findViewById(R.id.kakeibobtn3);
		kakeibobtn3.setOnClickListener(this);
		
        
		// 表示用TextView
		mTextView = (TextView) findViewById(R.id.display);
 
		// Button
		mButton = new Button[mId.length];
 
		// Buttonの取り込みとイベントのはりつけ
		for (int i = 0; i < mId.length; i++) {
			// buttonを取り込む
			mButton[i] = (Button) findViewById(mId[i]);
			// buttonのイベント処理
			mButton[i].setOnClickListener(this);
		
        }
		calcArray = new ArrayList<String>(); 
		signArray = new ArrayList<String>(); 

    }

	@Override
	public void onClick(View view) {
		// TODO 自動生成されたメソッド・スタブ
		// 押されたボタンがどのボタンかを判定
		
				switch(view.getId()){
				
				case R.id.kakeibobtn3:
					Intent intent2 = new Intent();
					intent2.setClassName("kadai.kakeibo", "kadai.kakeibo.MainActivity");
					startActivity(intent2);
					
				}
		
				for (int i = 0; i < mId.length; i++) {
					if (view.equals(mButton[i])) {
						String nowValue = mTextView.getText().toString();
						// CLEAR
						if (i == KEY_CLEAR) {
							mTextView.setText("");
							total = 0;
							beforeStatus = KEY_CLEAR;
						}
						// = 
						else if (i == KEY_EQUAL && nowValue.length() > 0) { 
						 		nowValue = checkDisplay(nowValue); 
						 		calcArray.add(nowValue); 
						 		double ans = calc(); 
								mTextView.setText(Double.toString(ans)); 
								calcArray.clear(); 
								signArray.clear(); 
								beforeStatus = i; 

						}

						// + 
						else if (i == KEY_PLUS && nowValue.length() > 0) { 
								calcArray.add(nowValue); 
								signArray.add("+"); 
						 					beforeStatus = KEY_PLUS; 
						} 
						 				// - 
						 				else if (i == KEY_MINUS && nowValue.length() > 0) { 
						 					calcArray.add(nowValue); 
						 					signArray.add("-"); 
						 					beforeStatus = KEY_MINUS; 
						 				} 
		 				// * 
						 				else if (i == KEY_kake && nowValue.length() > 0) { 
						 					calcArray.add(nowValue); 
						 					signArray.add("×"); 
						 					beforeStatus = KEY_kake; 
						 				} 
		 				// ÷
						 				else if (i == KEY_waru && nowValue.length() > 0) { 
						 					calcArray.add(nowValue); 
						 					signArray.add("÷"); 
						 					beforeStatus = KEY_waru; 
						 				}
						
						 				// . 
						 				else if (i == KEY_TEN) { 
						 					// .キーを押した時、演算キーが押されていた場合 
						 					nowValue = checkDisplay(nowValue); 
						 					// いきなり.キーが押された場合 
						 					if (nowValue.length() == 0) { 
						 						nowValue = "0."; 
						 					} else { 
						 						nowValue = nowValue + "."; 
						 					} 
						 					mTextView.setText(nowValue); 
						 					beforeStatus = i; 
						 				} 

						// 数字 
										else if (i < 10) { 
						 					nowValue = checkDisplay(nowValue); 
						 					// 0しか入力されていない場合は0が２個以上並ばないようにする 
						 					if (nowValue.equals("0") && i == 0) { 
						 						return; 
						 					} else if (nowValue.equals("0") && i != 0) { 
						 						nowValue = ""; 
						 					} 
						 
						 
						 					nowValue = nowValue + i; 
						 					mTextView.setText(nowValue); 
						 					beforeStatus = i; 
						 				} 
						 				break; 

					}
				}
		
	}
	
	//演算が行われていた場合を確認する 
	//遅れた状態でディスプレイの値を初期化する 
 	private String checkDisplay(String now) { 
 		if (beforeStatus == KEY_PLUS  
 				|| beforeStatus == KEY_MINUS
 				|| beforeStatus == KEY_kake 
 				|| beforeStatus == KEY_waru 
 				|| beforeStatus == KEY_EQUAL) { 
 			mTextView.setText(""); 
 			return "0"; 
 		} 
 		return now; 
 	} 
// 計算
	private double calc() {
		// TODO 自動生成されたメソッド・スタブ
		if (calcArray.size() == 0) { 
 			return 0.0; 
 		} 
 		if (calcArray.size() == 1) { 
 			return Double.parseDouble(calcArray.get(0)); 
 		} 
 		double passive = Double.parseDouble(calcArray.get(0)); 
 		double active; 
 		int j = 0; 
 		for (int i = 1; i < calcArray.size(); i++) { 
 			active = Double.parseDouble(calcArray.get(i)); 
 			if (signArray.get(j).equals("+")) { 
 				passive += active; 
 			} else if (signArray.get(j).equals("-")){ 
 				passive -= active;
 			} else if (signArray.get(j).equals("×")){
 				passive *= active;
 			} else if (signArray.get(j).equals("÷")) {
 				passive /= active;
 			} 
 			j++; 
 		} 
 		return passive; 

	}

}
