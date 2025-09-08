package onlineTravelBooking.payment_service.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorPaymentService {

    private final RazorpayClient client;

    public RazorPaymentService() throws Exception {
        // ✅ Initialize Razorpay client once
        this.client = new RazorpayClient("rzp_test_m17StH2RvJgYRu", "LPizoSMeqePb0Bx8YCyIATF0");
    }

    public Order createOrder(int amount) throws Exception {
        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // Razorpay expects paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());
        options.put("payment_capture", 1);

        return client.orders.create(options);
    }
}
