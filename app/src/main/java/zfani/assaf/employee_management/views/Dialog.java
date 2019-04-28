package zfani.assaf.employee_management.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.lifecycle.ViewModelProviders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zfani.assaf.employee_management.R;
import zfani.assaf.employee_management.models.Employee;
import zfani.assaf.employee_management.viewmodels.MainViewModel;

public class Dialog extends AppCompatDialog {

    @BindView(R.id.etId)
    EditText etId;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etStartDate)
    EditText etStartDate;
    private final MainViewModel mainViewModel;
    private final boolean isEdit;

    Dialog(Context context, boolean isEdit) {
        super(context);
        setContentView(R.layout.dialog_details);
        ButterKnife.bind(this);
        mainViewModel = ViewModelProviders.of((AppCompatActivity) context).get(MainViewModel.class);
        this.isEdit = isEdit;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Calendar calendar = Calendar.getInstance();
        etStartDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), (view1, year, month, dayOfMonth) -> etStartDate.setText(new StringBuilder().append(dayOfMonth).append("/").append(month + 1).append("/").append(year)), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            calendar.set(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getContext().getString(R.string.dialog_cancel), datePickerDialog);
            datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getContext().getString(R.string.dialog_confirm), datePickerDialog);
            datePickerDialog.show();
        });
        if (isEdit) {
            Employee emp = mainViewModel.getSelectedEmployee();
            etId.setText(emp.getId());
            etName.setText(emp.getName());
            etPhone.setText(emp.getPhone());
            etStartDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(emp.getStartDate()));
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.btnConfirm)
    void confirm() {
        mainViewModel.addOrUpdateEmployee(isEdit, etId.getText().toString(), etName.getText().toString(), etPhone.getText().toString(), getDate());
        dismiss();
    }

    private long getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date date = null;
        try {
            date = dateFormat.parse(etStartDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date == null ? 0 : date.getTime();
    }
}
