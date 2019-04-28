package zfani.assaf.employee_management.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import zfani.assaf.employee_management.R;
import zfani.assaf.employee_management.interfaces.OnItemSelectedListener;
import zfani.assaf.employee_management.models.Employee;

public class EmployeeAdapter extends FirebaseRecyclerAdapter<Employee, EmployeeAdapter.EmployeeViewHolder> {

    private final OnItemSelectedListener onItemSelectedListener;

    public EmployeeAdapter(OnItemSelectedListener onItemSelectedListener) {
        super(new FirebaseRecyclerOptions.Builder<Employee>().setQuery(FirebaseDatabase.getInstance().getReference().child("Employees").orderByChild("startDate"), Employee.class).build());
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EmployeeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull EmployeeViewHolder viewHolder, int position, @NonNull Employee employee) {
        viewHolder.tvId.setText(employee.getId());
        viewHolder.tvName.setText(employee.getName());
        viewHolder.tvPhone.setText(employee.getPhone());
        viewHolder.tvStartDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(employee.getStartDate()));
        designRow(employee.isSelected(), viewHolder);
        viewHolder.itemView.setOnClickListener(view -> {
            employee.setSelected(!employee.isSelected());
            notifyDataSetChanged();
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(employee);
            }
        });
    }

    private void designRow(boolean isSelected, EmployeeViewHolder viewHolder) {
        Context context = viewHolder.itemView.getContext();
        for (TextView textView : viewHolder.getViews()) {
            textView.setTextColor(ContextCompat.getColor(context, isSelected ? android.R.color.white : android.R.color.darker_gray));
        }
        viewHolder.itemView.setBackgroundResource(isSelected ? android.R.color.black : viewHolder.getAdapterPosition() % 2 != 0 ? R.color.colorGreenLight : R.color.colorGrey);
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvId)
        TextView tvId;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvPhone)
        TextView tvPhone;
        @BindView(R.id.tvStartDate)
        TextView tvStartDate;

        EmployeeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        TextView[] getViews() {
            return new TextView[]{tvId, tvName, tvPhone, tvStartDate};
        }
    }
}
