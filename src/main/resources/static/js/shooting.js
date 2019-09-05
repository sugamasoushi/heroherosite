"use strict"

var canvas, ctx;
var img_player;//画像保持用
var img_player_bullet;
var img_enemy;
var ENEMIES = 10;
var BULLETS = 5;
var FIRE_INTERVAL = 20;
var killed = 0;
var STAR_INTERVAL = 20;

var player_x,player_y;
var player_bullets_x = new Array(BULLETS);
var player_bullets_y = new Array(BULLETS);
var player_fire_interval=0;
var player_star_interval=0;

var enemies_x = new Array(ENEMIES);
var enemies_y = new Array(ENEMIES);

var player_hp;
var player_bullets_hp = new Array(BULLETS);

var enemies_hp = new Array(ENEMIES);

var FPS = 30;
var MSPF = 1000/FPS;

var KEYS = new Array(256);
//全てのキーをfalseで初期化
for(var i=0;i<KEYS.length;i++){
	KEYS[i] = false;
}

var hitCheck = function(x1, y1, obj1, x2, y2, obj2) {
    var cx1, cy1, cx2, cy2, r1, r2, d;
    // 中心座標の取得
    cx1 = x1 + obj1.width/2;
    cy1 = y1 + obj1.height/2;
    cx2 = x2 + obj2.width/2;
    cy2 = y2 + obj2.height/2;
    // 半径の計算
    r1 = (obj1.width+obj1.height)/4;
    r2 = (obj2.width+obj2.height)/4;
    // 中心座標同士の距離の測定
    // Math.sqrt(d) -- dのルートを返す
    // Math.pow(x, a) -- xのa乗を返す
    d = Math.sqrt(Math.pow(cx1-cx2, 2) + Math.pow(cy1-cy2, 2));
    // 当たっているか判定
    // ちなみに `return r1+r2 > d;` とだけ書いてもOK
    if(r1 + r2 > d) {
        // 当たってる
        return true;
    } else {
        // 当たっていない
        return false;
    }
};

var mainloop = function() {
    // 処理開始時間を保存
    var startTime = new Date();
    
    //プレイヤー移動
    movePlayer();
 // プレイヤーの弾の移動処理
    movePlayerBullets();
    
    //敵キャラの移動距離
    moveEnemies();
    
 // プレイヤーと敵キャラの当たり判定（プレイヤーが生きている場合）
    // かつプレイヤーが無敵ではない場合
    if(player_hp > 0 && player_star_interval == 0) {
        for(var i=0; i<ENEMIES; i++) {
            // 敵が生きている場合のみ判定する
            if(enemies_hp[i] > 0) {
                if(hitCheck(player_x, player_y, img_player,
                            enemies_x[i], enemies_y[i], img_enemy)){
                    // ...

                    // プレイヤーを無敵状態にする
                    player_star_interval = STAR_INTERVAL;
                }
            }
        }
    }
 // プレイヤーの無敵インターバルを減少させる
    if(player_star_interval > 0) {
        player_star_interval--;
    }
 // プレイヤー弾と敵キャラの当たり判定（プレイヤーが生きている場合）
    if(player_hp > 0) {
        for(var i=0; i<ENEMIES; i++) {
            // 敵が死んでいる場合はスルーする
            if(enemies_hp[i] <= 0) {
                continue;
            }
            for(var j=0; j<BULLETS; j++) {
                // 弾が死んでいる場合はスルーする
                if(player_bullets_hp[j] <= 0) {
                    continue;
                }
                if(hitCheck(player_bullets_x[j],
                            player_bullets_y[j],
                            img_player_bullet,
                            enemies_x[i],
                            enemies_y[i],
                            img_enemy)){
                    // 当たっているのでお互いのHPを1削る
                    player_bullets_hp[j] -= 1;
                    enemies_hp[i] -=1;
                    if(enemies_hp[i]==0){
                    	killed++;
                    }
                }
            }
        }
    }

    // 描画処理
    redraw();

    // 処理経過時間および次のループまでの間隔を計算
    var deltaTime = (new Date()) - startTime;
    var interval = MSPF - deltaTime;
    if(interval > 0) {
        // 処理が早すぎるので次のループまで少し待つ
        setTimeout(mainloop, interval);
    } else {
        // 処理が遅すぎるので即次のループを実行する
        mainloop();
    }
};

window.onkeydown = function(e){
	KEYS[e.keyCode] = true;
	console.log(e.keyCode + "番のキーが押されたよ");
}
window.onkeyup = function(e){
	KEYS[e.keyCode] = false;
}

var movePlayer = function(){
	var SPEED = 2;
	var RIGHT = 39;
	var LEFT = 37;
	var UP = 38;
	
	if(KEYS[RIGHT] && player_x + img_player.width < canvas.width){
		player_x += SPEED;
		//console.log(player_x);
	}
	if(KEYS[LEFT] && player_x > 0){
		player_x -= SPEED;
		//console.log(player_x);
	}
	
	if(KEYS[UP]) {
        // 未使用の弾があれば発射する
        for(var i=0; i<BULLETS; i++) {
            if(player_bullets_hp[i] == 0) {
                // 弾の初期位置はプレイヤーと同じ位置にする
                player_bullets_x[i] = player_x;
                player_bullets_y[i] = player_y;
                // 弾のHPを1にする。これにより次のループから描画や移動処理
                // が行われるようになる
                player_bullets_hp[i] = 1;
                // 弾は打ったのでループを抜ける
                // ループ処理を途中でやめる場合は `break` を使う
                break;
            }
        }
    }
	
	// プレイヤーがはみ出てしまった場合は強制的に中に戻す
    if(player_x < 0) {
        player_x = 0;
    } else if (player_x + img_player.width > canvas.width) {
        player_x = canvas.width - img_player.width;
    }
    
 // スペースキーが押され、なおかつ発射インターバルが0の時だけ発射する
    if(KEYS[UP] && player_fire_interval == 0) {
        // 未使用の弾があれば発射する
        for(var i=0; i<BULLETS; i++) {
            if(player_bullets_hp[i] == 0) {
                // ...
                // 弾を打ったので発射インターバルの値を上げる
                player_fire_interval = FIRE_INTERVAL;
                // 弾は打ったのでループを抜ける
                // ループ処理を途中でやめる場合は `break` を使う
                break;
            }
        }
    }
    // 発射インターバルの値が0より大きい場合は値を減らす。
    if(player_fire_interval > 0) {
        // 変数++; や 変数--; はそれぞれ1増やす、減らすという処理
        // そのため下記は `player_fire_interval -= 1;`と等価
        player_fire_interval--;
    }
}
var movePlayerBullets = function() {
    // 上下左右の移動速度を定義
    var SPEED = -6;

    // 各弾ごとに処理を行う
    for(var i=0; i<BULLETS; i++) {
        // ヒットポイントを確認し、生きている場合のみ処理をする
        if(player_bullets_hp[i] <= 0) {
            // ループの残りのステップを無視して次のループに行く場合
            // は `continue` を指定する
            continue;
        }

        // 弾のy座標を少し増やす（減らす）
        player_bullets_y[i] += SPEED;

        // 弾が上画面にはみ出た場合はHPを0にして未使用状態に戻す
        if (player_bullets_y[i] < img_player_bullet.height) {
            player_bullets_hp[i] = 0;
        }
    }
};
var moveEnemies = function(){
	var SPEED = 2;
	
	for(var i=0; i<ENEMIES; i++){
		enemies_y[i] += SPEED;
		
		if(enemies_hp[i] <= 0){
			continue;
		}
		if(enemies_y[i] > canvas.height){
			enemies_y[i] = -img_enemy.height;
			enemies_x[i] = Math.random() * (canvas.width - img_enemy.width);
		}
	}
}

//ページ読み込み時に呼び出される、F5とか
//初期化処理
window.onload = function(){
	//idからキャンパスを取得しcanvas変数に代入
	//オブジェクト = documten.getElementById('id');
	canvas = document.getElementById('screen');
	
	//2次元用の描画コンテキストを取得
	ctx = canvas.getContext('2d');
	
	img_player = document.getElementById('player');
	img_player_bullet = document.getElementById('player_bullet');
	img_enemy = document.getElementById('enemy');

	//playerの位置を指定
	player_x = (canvas.width - player.width) / 2;
	player_y = (canvas.height - player.height) - 20;
	player_hp = 10;
	
	// 弾の初期位置およびHPを指定
    for(var i=0; i<BULLETS; i++) {
        player_bullets_x[i] = 0;
        player_bullets_y[i] = 0;
        player_bullets_hp[i] = 0;
    }
	//敵キャラの初期位置
	for(var i=0; i<ENEMIES; i++){
		enemies_x[i] = Math.random() * (canvas.width - img_enemy.width);
		enemies_y[i] = Math.random() * (canvas.height - img_enemy.height);
		enemies_hp[i] = 2;
	}
	
	// タイトルループを開始する（メインループでは無いことに注意）
    titleloop();
};

var redraw = function(){
	
	//キャンパスをクリアする
	ctx.clearRect(0,0,canvas.width, canvas.height);
	
	// 生きている場合だけ新しい位置にプレイヤーを描画
    if(player_hp > 0) {
        // 透過度を変えるのでコンテキストの状態を保存
        ctx.save();
        // 無敵時間の状態に応じて描画の透過度を変更
        if(player_star_interval % 2 != 0) {
            // 半透明に描画する
            ctx.globalAlpha = 0.5;
        }
        ctx.drawImage(img_player, player_x, player_y);
        // コンテキストの状態を戻す
        ctx.restore();
    }
	
	// 弾の画像を (bullets_x[i], bullets_y[i]) の位置に表示
    for(var i=0; i<BULLETS; i++) {
        // 生きている場合だけ描画
        if(player_bullets_hp[i] > 0) {
            ctx.drawImage(img_player_bullet,
                          player_bullets_x[i],
                          player_bullets_y[i]);
        }
    }
	
	//敵キャラの位置を(enemies_x[i],enemies_y[i])の位置
	for(var i=0; i<ENEMIES; i++){
		if(enemies_hp[i] > 0){
			ctx.drawImage(img_enemy,enemies_x[i],enemies_y[i]);
		}
	}
	
	// コンテキストの状態を保存（fillStyleを変えたりするので）
    ctx.save();
    // HPの最大値（10）x 5 の短形を描画（白）
    ctx.fillStyle = '#fff';
    ctx.fillRect(10, canvas.height-10, 10 * 5, 5);
    // 残りHP x 5 の短形を描画（赤）
    ctx.fillStyle = '#f00';
    ctx.fillRect(10, canvas.height-10, player_hp * 5, 5);
    // コンテキストの状態を復元
    ctx.restore();
    
 // 「倒した敵の数/全敵の数」という文字列を作成
    var text = "Killed: " + killed + "/" + ENEMIES;
    // 文字列の（描画）横幅を計算する
    var width = ctx.measureText(text).width;
    // 文字列を描画（白）
    ctx.fillStyle = '#fff';
    ctx.fillText(text,
                 canvas.width - 10 - width,
                 canvas.height - 10);
    
 // プレイヤーが死んでいた場合ゲームオーバー画面を表示する
    if(player_hp <= 0){
        // 全体を半透明の黒い四角で覆う（オーバーレイ）
        ctx.globalAlpha = 0.5;
        ctx.fillStyle = '#000';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        ctx.globalAlpha = 1.0;

        // 真ん中に大きな文字でゲームオーバー（赤）と表示する
        ctx.font = '20px sans-serif';
        ctx.textBaseline = 'middle';    // 上下位置のベースラインを中心に
        ctx.fillStyle = '#f00';
        text = "Game Over";
        width = ctx.measureText(text).width;
        ctx.fillText(text,
                     (canvas.width - width) / 2,
                     canvas.height / 2);
    }
    
 // 敵を殲滅していた場合はゲームクリア画面を表示
    else if(killed == ENEMIES){
        // 全体を半透明の黒い四角で覆う（オーバーレイ）
        ctx.globalAlpha = 0.5;
        ctx.fillStyle = '#000';
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        ctx.globalAlpha = 1.0;

        // 真ん中に大きな文字でゲームクリア（白）と表示する
        ctx.font = '20px sans-serif';
        ctx.textBaseline = 'middle';    // 上下位置のベースラインを中心に
        ctx.fillStyle = '#fff';
        text = "Game Clear";
        width = ctx.measureText(text).width;
        ctx.fillText(text,
                     (canvas.width - width) / 2,
                     canvas.height / 2);
    }

    // コンテキストの状態を復元
    ctx.restore();

};

//タイトルループを定義
var titleloop_blinker = 0;
var titleloop = function() {
    // 処理開始時間を保存
    var startTime = new Date();

    // キャンバスをクリアする
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    ctx.save();
    // ラインで装飾
    // 参考: http://www.html5.jp/canvas/ref/method/lineTo.html
    ctx.strokeStyle = '#fff';
    ctx.beginPath();
    ctx.moveTo(20, 100);
    ctx.lineTo(canvas.width-20, 100);
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(20, 145);
    ctx.lineTo(canvas.width-20, 145);
    ctx.stroke();
    ctx.strokeStyle = '#444';
    ctx.beginPath();
    ctx.moveTo(30, 90);
    ctx.lineTo(canvas.width-30, 90);
    ctx.stroke();
    ctx.beginPath();
    ctx.moveTo(30, 155);
    ctx.lineTo(canvas.width-30, 155);
    ctx.stroke();

    var text, width;
    // JavaScript Shooting と表示
    ctx.font = '20px serif';
    ctx.textBaseline = 'middle';    // 上下位置のベースラインを中心に
    ctx.fillStyle = '#fff';
    text = "JavaScript Shooting";
    width = ctx.measureText(text).width;
    ctx.fillText(text, (canvas.width - width) / 2, 120);

    // Hit SPACE to Start と表示
    titleloop_blinker++;
    if(titleloop_blinker > 20) {
        // 点滅処理様に透過度を調整
        ctx.globalAlpha = 0.5;
        // 30を超えていたら0に戻す
        if(titleloop_blinker > 30) {
            titleloop_blinker = 0;
        }
    }
    ctx.font = '12px sans-serif';
    ctx.textBaseline = 'middle';    // 上下位置のベースラインを中心に
    ctx.fillStyle = '#ddd';
    text = "Hit SPACE to Start";
    width = ctx.measureText(text).width;
    ctx.fillText(text, (canvas.width - width) / 2, 240);
    ctx.globalAlpha = 1.0;
    ctx.restore();

    // スペースが押されていた場合は mainloop を呼び出して、titleloopを終了
    var UP = 38;
    if(KEYS[UP]) {
        // メインループを呼び出す
        mainloop();
        // 継続処理をせずに関数を終了（titleloopを抜ける）
        return;
    }
    // 処理経過時間および次のループまでの間隔を計算
    var deltaTime = (new Date()) - startTime;
    var interval = MSPF - deltaTime;
    if(interval > 0) {
        // 処理が早すぎるので次のループまで少し待つ
        setTimeout(titleloop, interval);
    } else {
        // 処理が遅すぎるので即次のループを実行する
        // Note: titleloop()を直接呼び出すとフリーズします。
        setTimeout(titleloop, 0);
    }
};