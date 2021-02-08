package com.example.coverbackgroundsolution;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.coverbackgroundsolution.repository.NumberRepository;

// ViewModel : Application Context 접근이 안된다.
// AndroidViewModel : Application Context 접근이 가능하다.
public class MainViewModel extends AndroidViewModel {
    private final NumberRepository repository;
    public MutableLiveData<Integer> progressLiveData = new MutableLiveData<>(0);
    private final String TAG = MainViewModel.class.getSimpleName();

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = new NumberRepository(
                ((App) application).executorService,
                ((App) application).mainThreadHandler
        );
    }

    public void longTask() {
        repository.longTask(result -> {
            if (result instanceof Result.Success) {
                progressLiveData.postValue(((Result.Success<Integer>) result).data);        // UI thread에 있는 데이터를 접근 하기에 postValue()를 써야 함
            } else if (result instanceof Result.Error) {
                Toast.makeText(getApplication(), TAG + " Error! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
