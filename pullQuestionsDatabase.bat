del QuizAppQuestions

adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/

adb shell run-as com.example.stanislavfrolov.quizapp chmod 777 /data/data/com.example.stanislavfrolov.quizapp/databases/QuizAppQuestions

adb shell cp /data/data/com.example.stanislavfrolov.quizapp/databases/QuizAppQuestions /sdcard/

adb pull /sdcard/QuizAppQuestions
