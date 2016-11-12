# QuizApp

This is a template for an Android app to conduct surveys and save data in SQLite database.

There are two databases. One for the questions and one for collecting the answers.

## Add your own questions

To add questions to the app go to the `addQuestionsToDatabase()` function in the `QuestionDatabaseHelper` class.

A Question is added through:

`allQuestions.add(new Question("YOUR QUESTION", "OPTION A", "OPTION B", "OPTON C"));`

## Pull database with adb

Connect smartphone to your computer.

Make sure adb is in your path.

Download and run the scripts `pullQuestionsDatabase.bat` and `pullAnswersDatabase.bat`.

Alternatively, use the following commands to pull and save the database.

`del [database-name]`

`adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/`

`adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/[database-name]`

`adb shell cp /data/data/com.example.stanislavfrolov.quizapp/databases/[database-name] /sdcard/`

`adb pull /sdcard/[database-name]`

For the questions database:
[database-name] = QuizAppQuestions

For the answers database:
[database-name] = QuizAppUser

## View database

To view the contents of the databases one can use the DB Browser for SQLite.

Download here http://sqlitebrowser.org/.
