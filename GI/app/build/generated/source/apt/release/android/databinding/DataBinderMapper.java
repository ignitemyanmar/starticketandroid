
package android.databinding;
import com.ignite.mdm.ticketing.agent.callcenter.BR;
class DataBinderMapper {
    final static int TARGET_MIN_SDK = 16;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_invoice:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.ItemInvoiceBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.dialog_invoice:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.DialogInvoiceBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_threeday_sales:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityThreedaySalesBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_outstanding_bookings:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.ItemOutstandingBookingsBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_out_standingbooking:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityOutStandingbookingBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.dialog_chooser_place:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.DialogChooserPlaceBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_invoice:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityInvoiceBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_change_password:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityChangePasswordBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_invoice_detail_activty:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.ActivityInvoiceDetailActivtyBinding.bind(view, bindingComponent);
                case com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_invoice_detail:
                    return com.ignite.mdm.ticketing.agent.callcenter.databinding.ItemInvoiceDetailBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case 2114371319: {
                if(tag.equals("layout/item_invoice_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_invoice;
                }
                break;
            }
            case 857765292: {
                if(tag.equals("layout/dialog_invoice_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.dialog_invoice;
                }
                break;
            }
            case 1647683655: {
                if(tag.equals("layout/activity_threeday_sales_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_threeday_sales;
                }
                break;
            }
            case -1182397065: {
                if(tag.equals("layout/item_outstanding_bookings_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_outstanding_bookings;
                }
                break;
            }
            case -1295536478: {
                if(tag.equals("layout/activity_out_standingbooking_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_out_standingbooking;
                }
                break;
            }
            case 1800618882: {
                if(tag.equals("layout/dialog_chooser_place_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.dialog_chooser_place;
                }
                break;
            }
            case -367255181: {
                if(tag.equals("layout/activity_invoice_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_invoice;
                }
                break;
            }
            case -1697258416: {
                if(tag.equals("layout/activity_change_password_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_change_password;
                }
                break;
            }
            case -441716572: {
                if(tag.equals("layout/activity_invoice_detail_activty_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_invoice_detail_activty;
                }
                break;
            }
            case -321472741: {
                if(tag.equals("layout/item_invoice_detail_0")) {
                    return com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_invoice_detail;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}