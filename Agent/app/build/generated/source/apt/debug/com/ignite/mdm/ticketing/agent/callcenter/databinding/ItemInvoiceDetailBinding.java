package com.ignite.mdm.ticketing.agent.callcenter.databinding;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.BR;
import android.view.View;
public class ItemInvoiceDetailBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.invoice_id, 1);
        sViewsWithIds.put(R.id.money, 2);
        sViewsWithIds.put(R.id.time, 3);
        sViewsWithIds.put(R.id.commission, 4);
        sViewsWithIds.put(R.id.city, 5);
        sViewsWithIds.put(R.id.name_seats, 6);
        sViewsWithIds.put(R.id.leave_date, 7);
    }
    // views
    public final android.widget.TextView city;
    public final android.widget.TextView commission;
    public final android.widget.TextView invoiceId;
    public final android.widget.TextView leaveDate;
    private final android.widget.LinearLayout mboundView0;
    public final android.widget.TextView money;
    public final android.widget.TextView nameSeats;
    public final android.widget.TextView time;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemInvoiceDetailBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.city = (android.widget.TextView) bindings[5];
        this.commission = (android.widget.TextView) bindings[4];
        this.invoiceId = (android.widget.TextView) bindings[1];
        this.leaveDate = (android.widget.TextView) bindings[7];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.money = (android.widget.TextView) bindings[2];
        this.nameSeats = (android.widget.TextView) bindings[6];
        this.time = (android.widget.TextView) bindings[3];
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

    public static ItemInvoiceDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemInvoiceDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ItemInvoiceDetailBinding>inflate(inflater, com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_invoice_detail, root, attachToRoot, bindingComponent);
    }
    public static ItemInvoiceDetailBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemInvoiceDetailBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ignite.mdm.ticketing.agent.callcenter.R.layout.item_invoice_detail, null, false), bindingComponent);
    }
    public static ItemInvoiceDetailBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ItemInvoiceDetailBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/item_invoice_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ItemInvoiceDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}