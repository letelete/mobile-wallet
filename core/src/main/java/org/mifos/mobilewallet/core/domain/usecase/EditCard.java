package org.mifos.mobilewallet.core.domain.usecase;

import android.util.Log;

import org.mifos.mobilewallet.core.base.UseCase;
import org.mifos.mobilewallet.core.data.fineract.api.GenericResponse;
import org.mifos.mobilewallet.core.data.fineract.repository.FineractRepository;
import org.mifos.mobilewallet.core.data.fineract.entity.savedcards.Card;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ankur on 22/May/2018
 */

public class EditCard extends UseCase<EditCard.RequestValues, EditCard.ResponseValue> {

    private final FineractRepository mFineractRepository;

    @Inject
    public EditCard(FineractRepository fineractRepository) {
        mFineractRepository = fineractRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        mFineractRepository.editSavedCard(requestValues.clientId, requestValues.card)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getUseCaseCallback().onError(e.toString());
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        Log.d("qxz", "onNext: card updated");
                        getUseCaseCallback().onSuccess(new ResponseValue());
                    }
                });

    }

    public static final class RequestValues implements UseCase.RequestValues {

        private final int clientId;
        private final Card card;

        public RequestValues(int clientId, Card card) {
            this.clientId = clientId;
            this.card = card;
        }
    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }
}
