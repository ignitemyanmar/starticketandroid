package com.ignite.mdm.ticketing.agent.callcenter.databinding;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.BR;
import android.view.View;
public class ActivityInvoiceDetailActivtyBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.invoice_id, 2);
        sViewsWithIds.put(R.id.total_amount, 3);
        sViewsWithIds.put(R.id.date, 4);
        sViewsWithIds.put(R.id.net_amount, 5);
        sViewsWithIds.put(R.id.commission, 6);
        sViewsWithIds.put(R.id.progress, 7);
        sViewsWithIds.put(R.id.recycler_view, 8);
    }
    // views
    public final android.widget.LinearLayout activityInvoiceDetailActivty;
    public final android.widget.TextView commission;
    public final android.widget.TextView date;
    public final android.widget.TextView invoiceId;
    public final android.widget.TextView netAmount;
    public final android.widget.ProgressBar progress;
    public final android.support.v7.widget.RecyclerView recyclerView;
    public final android.support.v7.widget.Toolbar toolbar;
    public final android.widget.TextView totalAmount;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityInvoiceDetailActivtyBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.activityInvoiceDetailActivty = (android.widget.LinearLayout) bindings[0];
        this.activityInvoiceDetailActivty.setTag(null);
        this.commission = (android.widget.TextView) bindings[6];
        this.date = (android.widget.TextView) bindings[4];
        this.invoiceId = (android.widget.TextView) bindings[2];
        this.netAmount = (android.widget.TextView) bindings[5];
        this.progress = (android.widget.ProgressBar) bindings[7];
        this.recyclerView = (android.support.v7.widget.RecyclerView) bindings[8];
        this.toolbar = (android.support.v7.widget.Toolbar) bindings[1];
        this.totalAmount = (android.widget.TextView) bindings[3];
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

    public static ActivityInvoiceDetailActivtyBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityInvoiceDetailActivtyBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityInvoiceDetailActivtyBinding>inflate(inflater, com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_invoice_detail_activty, root, attachToRoot, bindingComponent);
    }
    public static ActivityInvoiceDetailActivtyBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityInvoiceDetailActivtyBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_invoice_detail_activty, null, false), bindingComponent);
    }
    public static ActivityInvoiceDetailActivtyBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityInvoiceDetailActivtyBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_invoice_detail_activty_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityInvoiceDetailActivtyBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}