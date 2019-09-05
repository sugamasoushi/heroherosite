/* Eclipseのsql実行は設定がうまくいかず断念、cmdからコピペしていれた */ 

SET
CLIENT_ENCODING TO 'UTF-8';
DROP
    database IF EXISTS heroherosite;
CREATE
    database heroherosite;

DROP
    TABLE IF EXISTS login_user_transaction;
CREATE
    TABLE login_user_transaction(
        id SERIAL NOT NULL PRIMARY KEY
        ,login_id VARCHAR(16) UNIQUE
        ,login_pass VARCHAR(16)
        ,user_name VARCHAR(50)
        ,admin_flg VARCHAR(1)
        ,insert_date TIMESTAMP
        ,update_date TIMESTAMP
    );

DROP
    TABLE IF EXISTS item_info_transaction;
CREATE
    TABLE item_info_transaction(
        id SERIAL NOT NULL PRIMARY KEY
        ,product_id INT UNIQUE
        ,item_name VARCHAR(30)
        ,item_price INT
        ,item_stock INT
        ,product_description VARCHAR(255)
        ,category_id INT
        ,image_file_path VARCHAR(100)
        ,image_file_name VARCHAR(50)
        ,release_date TIMESTAMP
        ,release_company VARCHAR(50)
        ,status SMALLINT
        ,insert_date TIMESTAMP
        ,update_date TIMESTAMP
    );

DROP
    TABLE IF EXISTS user_buy_item_transanction;
CREATE
    TABLE user_buy_item_transanction(
        id SERIAL NOT NULL PRIMARY KEY
        ,item_transaction_id INT
        ,total_price INT
        ,total_count INT
        ,user_master_id VARCHAR(16)
        ,pay VARCHAR(30)
        ,insert_date TIMESTAMP
        ,update_date TIMESTAMP
    );

DROP
    TABLE IF EXISTS user_contactform;
CREATE
    TABLE user_contactform(
        name VARCHAR(16)
        ,mail VARCHAR(30)
        ,age VARCHAR(16)
        ,comments VARCHAR(255)
    );

DROP
    TABLE IF EXISTS keijiban;
CREATE
    TABLE keijiban(
        name VARCHAR(16)
        ,mail VARCHAR(30)
        ,age VARCHAR(16)
        ,comments VARCHAR(255)
    );

DROP
    TABLE IF EXISTS cart_info;
CREATE
    TABLE cart_info(
        id SERIAL NOT NULL PRIMARY KEY
        ,user_id VARCHAR(16)
        ,product_id INT
        ,product_count INT
        ,insert_date TIMESTAMP
        ,update_date TIMESTAMP
    );

INSERT INTO login_user_transaction VALUES 
    (1,'guest','guest','guest','0',now(),now()),
    (2,'admin','admin','administrator','1',now(),now());

INSERT INTO item_info_transaction VALUES
(1,1,'ノートBook',100,8,'真っ白',1,'url','gazou',now(),'超株式会社',0,now(),now()),
(2,2,'鉛筆',10,50,'8B',1,'url','gazou',now(),'超株式会社',0,now(),now()),
(3,3,'java silver 黒本',1980,2,'これで就職',1,'url','gazou',now(),'超株式会社',0,now(),now());

INSERT INTO user_contactform(name,mail,age,comments)VALUES('山田太郎','yahoo','30','山田です');
INSERT INTO keijiban(name,age,comments)VALUES('山田太郎','30','掲示板');
