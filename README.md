# QuizApp

This is a template for an Android app to conduct surveys and save data in SQLite database. There are two databases. One for the questions and one for collecting the answers.

## Pull database with adb

Make sure adb is in your path.
Use the following commands to pull and save the database from the connected smartphone.

`del [database-name]`

`adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/`

`adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/[database-name]`

`adb shell cp /data/data/com.example.stanislavfrolov.quizapp/databases/[database-name] /sdcard/`

`adb pull /sdcard/[database-name]`

For the questions database:
[database-name] = QuizAppQuestions

For the answers database:
[database-name] = QuizAppUser

Alternatively, save the commands in a .bat file and run the script.


