# User authentication via Facebook & OAuth2 (without password) using Spring Security
A SpringBoot application that uses OAuth2 to authenticate users via their Facebook accound instead of specifying a password
  
##Instructions to run the application:   
1. Make sure you have a `MySQL` server running.  
2. Run the command `gradle bootRun` while in the root folder.  
3. After application startup, navigate to `http://localhost:8080`.  
4. A web page displaying "Login via Facebook" and buttons for other calls to server will be loaded.  
5. The button "Hello" corresponding to the server side call `/hello` is accessible to all.  
6. The call `/hello/secure` and `/me` represented by "Hello Secure" and "Me" buttons respectively require a valid access token.  
7. You can check the behaviour of the calls before and after Logging in via Facebook.  
