package com.ignite.mdm.ticketing.agent.callcenter.databinding;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.BR;
import android.view.View;
public class ActivityOutStandingbookingBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.txt_spinner_items, 2);
        sViewsWithIds.put(R.id.txt_todate, 3);
        sViewsWithIds.put(R.id.btn_search, 4);
        sViewsWithIds.put(R.id.recycler_view, 5);
        sViewsWithIds.put(R.id.card, 6);
        sViewsWithIds.put(R.id.left_sell_status, 7);
        sViewsWithIds.put(R.id.sell_amount, 8);
        sViewsWithIds.put(R.id.left_money_status, 9);
        sViewsWithIds.put(R.id.left_money_amount, 10);
        sViewsWithIds.put(R.id.pay, 11);
    }
    // views
    public final android.widget.Button btnSearch;
    public final android.support.v7.widget.CardView card;
    public final android.widget.TextView leftMoneyAmount;
    public final android.widget.TextView leftMoneyStatus;
    public final android.widget.TextView leftSellStatus;
    private final android.widget.LinearLayout mboundView0;
    public final android.widget.Button pay;
    public final android.support.v7.widget.RecyclerView recyclerView;
    public final android.widget.TextView sellAmount;
    public final android.support.v7.widget.Toolbar toolbar;
    public final android.widget.Spinner txtSpinnerItems;
    public final android.widget.TextView txtTodate;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityOutStandingbookingBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.btnSearch = (android.widget.Button) bindings[4];
        this.card = (android.support.v7.widget.CardView) bindings[6];
        this.leftMoneyAmount = (android.widget.TextView) bindings[10];
        this.leftMoneyStatus = (android.widget.TextView) bindings[9];
        this.leftSellStatus = (android.widget.TextView) bindings[7];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pay = (android.widget.Button) bindings[11];
        this.recyclerView = (android.support.v7.widget.RecyclerView) bindings[5];
        this.sellAmount = (android.widget.TextView) bindings[8];
        this.toolbar = (android.support.v7.widget.Toolbar) bindings[1];
        this.txtSpinnerItems = (android.widget.Spinner) bindings[2];
        this.txtTodate = (android.widget.TextView) bindings[3];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
        }
        return false;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ActivityOutStandingbookingBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityOutStandingbookingBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityOutStandingbookingBinding>inflate(inflater, com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_out_standingbooking, root, attachToRoot, bindingComponent);
    }
    public static ActivityOutStandingbookingBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityOutStandingbookingBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_out_standingbooking, null, false), bindingComponent);
    }
    public static ActivityOutStandingbookingBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityOutStandingbookingBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_out_standingbooking_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityOutStandingbookingBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}