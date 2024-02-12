package com.example.Trading.constants;

public class ApiConstants {
    public static final String register ="/Register";
    public static final String userLogin ="/userLogin";

    public static final String addGroups = "/watchlist/add-groups";

    public static final String addSymbols ="/watchlist/add-symbols";

    public static final String getGroups ="/watchlist/get-groups";

    public static final String getSymbols ="/watchlist/get-symbols";

    public static final String symbolDetails ="/quote/get-symbolDetails";

    public static final String getAllSymbols ="/quote/get-allSymbols";

    public static final String AddOrder = "/trade/addOrder";
    public static final String CancelOrder = "/trade/cancelOrder";
    public static final String GetPortfolio = "/trade/getPortfolio";
    public static final String GetTradeHistory = "/trade/getTradeHistory";

    public static final String logout ="/user-logout";
    public static final String UpdateProfile ="/updateProfile/{emailOrMobileNumber}";

}
