#Android app to conduct surveys and save data in SQLite.

#Pull database with adb

Make sure adb is in you path.
Use the following commands pull and save the database from the connected smartphone.
Alternatively, save the commands in a .bat file and run the script.

'del [database-name]'

'adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/'

'adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/[database-name]'

'adb shell cp /data/data/com.example.stanislavfrolov.quizapp/databases/[database-name] /sdcard/'

'adb pull /sdcard/[database-name]'

For the questions database:
[database-name] = QuizAppQuestions

For the answers database:
[database-name] = QuizAppUser


