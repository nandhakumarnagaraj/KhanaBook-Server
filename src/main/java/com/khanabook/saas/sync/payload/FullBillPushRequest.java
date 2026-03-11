package com.khanabook.saas.sync.payload;

import com.khanabook.saas.entity.Bill;
import com.khanabook.saas.entity.BillItem;
import com.khanabook.saas.entity.BillPayment;
import lombok.Data;
import java.util.List;

@Data
public class FullBillPushRequest {
    private List<Bill> bills;
    private List<BillItem> items;
    private List<BillPayment> payments;
    private List<String> deletedBillIds;
    private List<String> deletedItemIds;
    private List<String> deletedPaymentIds;
}
