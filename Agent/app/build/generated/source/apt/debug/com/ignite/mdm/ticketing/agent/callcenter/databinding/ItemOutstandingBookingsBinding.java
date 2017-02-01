package com.ignite.mdm.ticketing.agent.callcenter.databinding;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.BR;
import android.view.View;
public class ItemOutstandingBookingsBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.date_container, 1);
        sViewsWithIds.put(R.id.date, 2);
        sViewsWithIds.put(R.id.day, 3);
        sViewsWithIds.put(R.id.day_qualifier, 4);
        sViewsWithIds.put(R.id.desc_container, 5);
        sViewsWithIds.put(R.id.place, 6);
        sViewsWithIds.put(R.id.ref, 7);
        sViewsWithIds.put(R.id.description, 8);
        sViewsWithIds.put(R.id.buy_date, 9);
    }
    // views
    public final android.widget.TextView buyDate;
    public final android.widget.TextView date;
    public final android.widget.LinearLayout dateContainer;
    public final android.widget.TextView day;
    public final android.widget.TextView dayQualifier;
    public final android.widget.LinearLayout descContainer;
    public final android.widget.TextView description;
    private final android.widget.RelativeLayout mboundView0;
    public final android.widget.TextView place;
    public final android.widget.TextView ref;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemOutstandingBookingsBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.buyDate = (android.widget.TextView) bindings[9];
        this.date = (android.widget.TextView) bindings[2];
        this.dateContainer = (android.widget.LinearLayout) bindings[1];
        this.day = (android.widget.TextView) bindings[3];
        this.dayQualifier = (android.widget.TextView) bindings[4];
        this.descContainer = (android.widget.LinearLayout) bindings[5];
        this.description = (android.widget.TextView) bindings[8];
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.place = (android.widget.TextView) bindings[6];
        this.ref = (android.widget.TextView) bindings[7];
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

    public static ItemOutstandingBookingsBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemOutstandingBookingsBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ItemOutstandingBookingsBinding>inflate(inflater, com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_outstanding_bookings, root, attachToRoot, bindingComponent);
    }
    public static ItemOutstandingBookingsBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemOutstandingBookingsBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_outstanding_bookings, null, false), bindingComponent);
    }
    public static ItemOutstandingBookingsBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemOutstandingBookingsBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/item_outstanding_bookings_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ItemOutstandingBookingsBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}