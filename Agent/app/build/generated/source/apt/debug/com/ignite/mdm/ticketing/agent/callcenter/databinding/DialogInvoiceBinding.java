package com.ignite.mdm.ticketing.agent.callcenter.databinding;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.BR;
import android.view.View;
public class DialogInvoiceBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.name, 1);
        sViewsWithIds.put(R.id.phone, 2);
        sViewsWithIds.put(R.id.car_class, 3);
        sViewsWithIds.put(R.id.seats, 4);
        sViewsWithIds.put(R.id.amount, 5);
        sViewsWithIds.put(R.id.remark, 6);
    }
    // views
    public final android.widget.TextView amount;
    public final android.widget.TextView carClass;
    private final android.widget.LinearLayout mboundView0;
    public final android.widget.TextView name;
    public final android.widget.TextView phone;
    public final android.widget.TextView remark;
    public final android.widget.TextView seats;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogInvoiceBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.amount = (android.widget.TextView) bindings[5];
        this.carClass = (android.widget.TextView) bindings[3];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (android.widget.TextView) bindings[1];
        this.phone = (android.widget.TextView) bindings[2];
        this.remark = (android.widget.TextView) bindings[6];
        this.seats = (android.widget.TextView) bindings[4];
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

    public static DialogInvoiceBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DialogInvoiceBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<DialogInvoiceBinding>inflate(inflater, com.ignite.mdm.ticketing.agent.callcenter.R.layout.dialog_invoice, root, attachToRoot, bindingComponent);
    }
    public static DialogInvoiceBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DialogInvoiceBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ignite.mdm.ticketing.agent.callcenter.R.layout.dialog_invoice, null, false), bindingComponent);
    }
    public static DialogInvoiceBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DialogInvoiceBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/dialog_invoice_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new DialogInvoiceBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}