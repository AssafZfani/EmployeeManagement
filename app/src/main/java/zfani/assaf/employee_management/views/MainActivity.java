package zfani.assaf.employee_management.views;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zfani.assaf.employee_management.R;
import zfani.assaf.employee_management.adapters.EmployeeAdapter;
import zfani.assaf.employee_management.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.srlEmployees)
    SwipeRefreshLayout srlEmployees;
    @BindView(R.id.rvEmployees)
    RecyclerView rvEmployees;
    @BindView(R.id.tvInsert)
    View tvInsert;
    @BindView(R.id.tvUpdate)
    View tvUpdate;
    @BindView(R.id.tvDelete)
    View tvDelete;
    private MainViewModel mainViewModel;
    private EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initView();
    }

    @OnClick(R.id.tvInsert)
    public void insert() {
        new Dialog(this, false).show();
    }

    @OnClick(R.id.tvUpdate)
    public void update() {
        new Dialog(this, true).show();
    }

    @OnClick(R.id.tvDelete)
    public void delete() {
        new AlertDialog.Builder(this).setMessage(getString(R.string.dialog_question)).setPositiveButton(getString(R.string.dialog_confirm), (dialog, which) -> mainViewModel.deleteEmployees()).setNegativeButton(getString(R.string.dialog_cancel), null).show();
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(() -> {
            srlEmployees.setRefreshing(false);
            mainViewModel.clearEmployeeList();
            rvEmployees.setAdapter(employeeAdapter = new EmployeeAdapter(employee -> mainViewModel.addOrRemoveEmployee(employee)));
            employeeAdapter.startListening();
        }, 1500);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onStop() {
        super.onStop();
        employeeAdapter.stopListening();
    }

    private void initView() {
        srlEmployees.setOnRefreshListener(this);
        RecyclerView.ItemAnimator itemAnimator = rvEmployees.getItemAnimator();
        if (itemAnimator != null) {
            rvEmployees.getItemAnimator().setAddDuration(750);
            rvEmployees.getItemAnimator().setRemoveDuration(750);
        }
        mainViewModel.hasEmployeeSelected().observe(this, hasEmployeeSelected -> {
            tvInsert.setEnabled(!hasEmployeeSelected);
            tvUpdate.setEnabled(hasEmployeeSelected);
            tvDelete.setEnabled(hasEmployeeSelected);
        });
        onRefresh();
    }
}
