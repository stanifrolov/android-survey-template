del QuizAppAnswers

adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/

adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/QuizAppAnswers

adb shell cp /data/data/com.example.stanislavfrolov.quizapp/databases/QuizAppAnswers /sdcard/

adb pull /sdcard/QuizAppAnswers