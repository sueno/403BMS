##*403 Book Management System*##

このプロジェクトは、403研究室用の図書管理システムです。  
PostgreSQLを利用して管理します。  

以下のコマンド・機能を追加する予定です  
  
1.addとrmをユーザー名とISBNで自動判別し、統一する  
2.lsuser(仮) ユーザー名一覧を表示する  
3.rとreturnで貸している本が1冊しかない場合、ユーザー名を省略できるようにする  
    

現在、以下のコマンドに対応しています。
  
     adduser [username]     ユーザーを追加します
     rmuser [username]      ユーザーを削除します

     add [ISBN]             本を追加します  
     inf  
     rm または remove [ISBN] 本を削除します 
     b  または borrow [ISBN] 本を借ります  
     r  または return [ISBN] 本を返します 
     ls または list          データベースの内容を表示します
     st または status        貸出中の本を一覧表示します
     h  または help          ヘルプを表示します
     clsまたは clear         画面をクリアします
     q  または exit          管理システムを終了します

以下のコマンドで実行できます

    java -jar 403BookManagementSystem/ExportedJAR/403BMS.jar
