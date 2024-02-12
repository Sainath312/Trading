package com.example.Trading.constants;


public class StringConstants {

    public static final String Saved="Csv File Successfully Added(Stored) into the Database";
    public static final String Created = "User Profile Created Successfully";
    public static final String UserExists="User Is Already Exists";
    public static final String User_Not_Exists = "User Is Not Exists PLs Try With Valid UserName And UserPassword";
    public static final String LogOut = "User Logout";
    public static final String NotLogin = "User Not LoggedIn";
    public static final String Login = "User Successfully loggedIn";
    public static final String SymbolNotExists = "Symbol Not Exists(or)Symbol Not Found";
    public static final String GroupIdNotExists = "Group Id Not Exists(or)GroupId Not Found";
    public static final String StockSymbolNotEmpty ="Stock symbol cannot be blank,must be fill symbol Name";
    public static final String OrderType ="Order type must be BUY or SELL";
    public static final String PriceConstraint ="Price must be a double greater than 0";
    public static final String FailedToLogOut = "User Failed to logout the session";
    public static final String FailedToSave = "Failed to Store the CSV File into the Database";

    public static final String Error ="{\"error\": \"Invalid session\"}";

    public static final String FindBuyOrdersQuery = "SELECT order FROM Orders order WHERE order.orderType = 'BUY'";
    public static final String FindSellOrdersQuery = "SELECT order FROM Orders order WHERE order.orderType = 'SELL'";

    public static final String Price="Price must be greater than 0(Zero)";
    public static final String Quantity = "Quantity must be an integer";
    public static final String StockSymbolLength ="Stock symbol length must be between 10 and 25 characters";
    public static final String UserName ="UserName cannot be blank";
    public static final String MailRequired ="Mail is required,it not be Empty";
    public static final String ValidMail= "Enter Valid Email";
    public static final String MobileNumberRequired= "Mobile is required and number can't be Empty";
    public static final String ValidMobileNumber= "Enter Valid India PhoneNumber";
    public static final String ValidPassword = "Enter Valid Password and Password can't be Empty";
    public static final String UpperCase="Password must contain at least one UpperCase Character";
    public static final String LowerCase="Password must contain at least one LowerCase Character";
    public static final String PasswordLength = "Password length must be at least 8 characters long";
    public static final String Numeric = "Password must contain at least one Numeric character";
    public static final String SpecialCharacter = "Password must contain at least one SpecialCharacter character";
    public static final String Key = "c2f35a546549346a069d163d55f27829de2e60acb99082ff67dcbde8d0891bdc";

    //  public static final String Login ="/user-login";
    public static final String TimeStampPattern = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String Success = "Successfully Order added";
    public static final String CancelOrder ="Order Canceled Successfully";
    public static final String OrderNotFound = "Order Not found";
    public static final String Pending = "PENDING";
    public static final String EXECUTED = "EXECUTED";
    public static final String Cancelled = "CANCELLED";
    public static final String Buy = "BUY";
    public static final String Sell = "SELL";
    public static final String OrderLike = "(?i)buy|sell";
    public static final String ValidOrderId = "Pls Enter Valid OrderId ";
    public static final String Symbol ="Symbol not found";
    public static final String OrderExecuted = "Order is executed";
    }
