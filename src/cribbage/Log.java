package cribbage;

//Built with the 'Singleton' design pattern
public class Log {
    //Attributes
    private static Log instance = null;

    public Log() {
    }

    //if currently no Log exists, make one, otherwise return the existing one
    //ensures only one Log class ever exists
    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }
}