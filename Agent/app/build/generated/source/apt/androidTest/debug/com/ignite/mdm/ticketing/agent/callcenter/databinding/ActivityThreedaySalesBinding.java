package com.ignite.mdm.ticketing.agent.callcenter.databinding;
import com.ignite.mdm.ticketing.agent.callcenter.R;
import com.ignite.mdm.ticketing.agent.callcenter.BR;
import android.view.View;
public class ActivityThreedaySalesBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.txt_fromdate, 2);
        sViewsWithIds.put(R.id.txt_todate, 3);
        sViewsWithIds.put(R.id.txt_spinner_items, 4);
        sViewsWithIds.put(R.id.btn_search, 5);
        sViewsWithIds.put(R.id.recycler_view, 6);
        sViewsWithIds.put(R.id.lst_threeday_sales, 7);
    }
    // views
    public final android.widget.Button btnSearch;
    public final android.widget.ListView lstThreedaySales;
    private final android.widget.LinearLayout mboundView0;
    public final android.support.v7.widget.RecyclerView recyclerView;
    public final android.support.v7.widget.Toolbar toolbar;
    public final android.widget.TextView txtFromdate;
    public final android.widget.Spinner txtSpinnerItems;
    public final android.widget.TextView txtTodate;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityThreedaySalesBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.btnSearch = (android.widget.Button) bindings[5];
        this.lstThreedaySales = (android.widget.ListView) bindings[7];
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerView = (android.support.v7.widget.RecyclerView) bindings[6];
        this.toolbar = (android.support.v7.widget.Toolbar) bindings[1];
        this.txtFromdate = (android.widget.TextView) bindings[2];
        this.txtSpinnerItems = (android.widget.Spinner) bindings[4];
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

    public static ActivityThreedaySalesBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityThreedaySalesBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityThreedaySalesBinding>inflate(inflater, com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_threeday_sales, root, attachToRoot, bindingComponent);
    }
    public static ActivityThreedaySalesBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityThreedaySalesBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.ignite.mdm.ticketing.agent.callcenter.R.layout.activity_threeday_sales, null, false), bindingComponent);
    }
    public static ActivityThreedaySalesBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityThreedaySalesBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_threeday_sales_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityThreedaySalesBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}