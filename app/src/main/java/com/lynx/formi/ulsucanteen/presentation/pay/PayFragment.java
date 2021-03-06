package com.lynx.formi.ulsucanteen.presentation.pay;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.lynx.formi.ulsucanteen.R;
import com.lynx.formi.ulsucanteen.domain.dataclass.Order;
import com.lynx.formi.ulsucanteen.other.events.HideLoaderEvent;
import com.lynx.formi.ulsucanteen.other.events.ShowMessageEvent;
import com.lynx.formi.ulsucanteen.other.utils.BackButtonListener;
import com.lynx.formi.ulsucanteen.other.utils.RouterProvider;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PayFragment extends MvpAppCompatFragment implements PayView, BackButtonListener {
    public static final String TAG = "PayFragment";
    private final String TITLE = "Оплата";
    @InjectPresenter
    PayPresenter presenter;

    private Button btnPay;

    @ProvidePresenter
    PayPresenter providePayPresenter() {
        return new PayPresenter(((RouterProvider) getParentFragment()).getRouter());
    }

    public static PayFragment newInstance(final Bundle args) {
        final PayFragment fragment = new PayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pay, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter.hideBNV();
        presenter.hideClearLootItem();

        btnPay = view.findViewById(R.id.btnPay);
        btnPay.setOnClickListener((v) -> presenter.payRequest());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setTitle(TITLE);
        presenter.showToolbarIcon();
    }

    @Override
    public boolean onBackPressed() {
        presenter.onBackPressed();
        return true;
    }


    @Override
    public void notifyOrderAdded(final String key) {
        // TODO оповестить ползователя ключом заказа
        EventBus.getDefault().post(new ShowMessageEvent(key));
        EventBus.getDefault().post(new HideLoaderEvent());
    }
}
