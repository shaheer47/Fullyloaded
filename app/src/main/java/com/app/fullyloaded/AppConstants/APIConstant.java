package com.app.fullyloaded.AppConstants;

public class APIConstant {

    private static APIConstant apiConstant;

    public synchronized static APIConstant getInstance() {
        if (apiConstant == null)
            apiConstant = new APIConstant();
        return apiConstant;
    }




    private final String BASE_URL = "https://fullyloaded.ie/api/";

    public final String HOME_PAGE = BASE_URL + "homePage";
    public final String SIGN_UP = BASE_URL + "signup";
    public final String LOG_IN = BASE_URL + "login";
    public final String CURRENT_COMPETITION = BASE_URL + "currentCompetition";
    public final String SAVE_BILLING_ADDRESS = BASE_URL + "saveBillingAddress";
    public final String SAVE_SHIPPING_ADDRESS = BASE_URL + "saveShippingAddress";

    public final String GET_SHIPPING_ADDRESS = BASE_URL + "getShippingAddress";
    public final String GET_Billing_ADDRESS = BASE_URL + "getBillingAddress";

    public final String CATEGORY_LIST = BASE_URL + "categoryList";
    public final String COMPETITION_DETAIL = BASE_URL + "competitionDetail";
    public final String CHOOSE_TICKET = BASE_URL + "chooseTicket";
    public final String ADD_CART = BASE_URL + "addCart";
    public final String CART_DETAILS = BASE_URL + "cartDetails";
    public final String RECENT_ORDER = BASE_URL + "recentOrder";
    public final String FORGOT_PASSWORD = BASE_URL + "forgotPassword";
//    public final String PREVIOUS_WINNERS = BASE_URL + "previousWinners";
    public final String PREVIOUS_WINNERS = BASE_URL + "pwinners";
    public final String SAVE_PAYMENT = BASE_URL + "savePayment";
    public final String REMOVE_CART_ITEM = BASE_URL + "removeCart";
    public final String CHECK_COUPAN = BASE_URL + "checkCoupan";
    public final String REMOVE_COUPAN = BASE_URL + "removeCoupan";
    public final String CHECKOUT = BASE_URL + "checkout";
    public final String PAYMENT_STATUS = BASE_URL + "payment-status";
    public final String LUMI_SAVE_PAYMENT = BASE_URL + "save-payment";





    public final String BRAIN_TREE_PAYMENT = "http://ilucenttechnolabs.com/projects/payment_braintree/braintree_payment.php";


    public static final String PAYPAL_CLIENT_ID = "AXNPskOhWNjRfJK1Q0oxMDV2jBYjiAqH2z7vQTXKhQ-XWBic9ah0rrjZdps7sWg1OXz_5L_EbSJ8b2sM";

    public static String FirstTimeLaunchApplication = "1";
}



