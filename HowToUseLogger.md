How to use logger in your classes

# Introduction #

1.Add to your class a new Logger instance , for example :

```
 private Logger logger = Logger.getLogger(<name of your class>.class);
```


2.If you want to log error/debug/info messages in your function or your exceptions, you should use next logger function:
```
   logger.error(<your error message>);
   logger.debug(<your error message>);
   logger.info(<your error message>);
```

your messages should contain detailed information about  exception to ease search for errors.



**For example:**public class Configuration {
    private static Logger logger = Logger.getLogger(Configuration.class);
...
 try {
            fis = new FileInputStream("core\\src\\main\\resources\\properties.properties");
            prop.load(fis);
        } catch (FileNotFoundException  e) {
            logger.error("File properties.properties has not been found ",e);
        } catch (IOException e) {
            logger.error("Work function named prop.load(FileInputStream f) has been interrupted",e);
...
       

 
 ```