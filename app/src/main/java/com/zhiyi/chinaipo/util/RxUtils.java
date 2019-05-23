package com.zhiyi.chinaipo.util;

import com.zhiyi.chinaipo.models.entity.ApiResponse;
import com.zhiyi.chinaipo.models.entity.WeatherEntity;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> rxSchedulerHelperObserable() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> SingleTransformer<T, T> rxSchedulerHelperSingle() {    //compose简化线程
        return new SingleTransformer<T, T>() {
            @Override
            public Single<T> apply(Single<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ApiResponse<T>, T> handleResults() {   //compose判断结果
        return new FlowableTransformer<ApiResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<ApiResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<ApiResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(ApiResponse<T> tHttpResponse) {
                        if (tHttpResponse.getCount() > 0) {
                            return createData(tHttpResponse.getResults());
                        } else {
                            return Flowable.error(new Exception("服务器返回error : " + tHttpResponse.getCount()));
                        }
                    }
                });
            }
        };
    }

    public static <T>FlowableTransformer<WeatherEntity<T>, T> handleResultss() {   //compose判断结果
        return new FlowableTransformer<WeatherEntity<T>, T>() {
            @Override
            public Flowable apply(Flowable<WeatherEntity<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<WeatherEntity<T>, Flowable<T>>() {
                    @Override
                    public Flowable apply(WeatherEntity<T> tHttpResponse) {
                        if (tHttpResponse.getCount()>0) {
                            return createData(tHttpResponse.getResult());
                        } else {
                            return Flowable.error(new Exception("服务器返回error : " + tHttpResponse.getCount()));
                        }
                    }
                });
            }
        };
    }

    public static <T> ObservableTransformer<ApiResponse<T>, T> handleResultsObserable() {   //compose判断结果
        return new ObservableTransformer<ApiResponse<T>, T>() {
            @Override
            public Observable<T> apply(Observable<ApiResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<ApiResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(ApiResponse<T> tHttpResponse) {
                        if (tHttpResponse.getCount() > 0) {
                            return createObservable(tHttpResponse.getResults());
                        } else {
                            return Observable.error(new Exception("服务器返回error : " + tHttpResponse.getCount()));
                        }
                    }
                });
            }
        };
    }


    public static <T> SingleTransformer<ApiResponse<List<T>>, T> getFirst() {   //compose判断结果
        return new SingleTransformer<ApiResponse<List<T>>, T>() {
            @Override
            public Single<T> apply(Single<ApiResponse<List<T>>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<ApiResponse<List<T>>, Single<T>>() {
                    @Override
                    public Single<T> apply(ApiResponse<List<T>> tHttpResponse) {
                        if (tHttpResponse.getCount() > 0) {
                            return null;
//                            return Single.fromFuture(.getResults().get(0));
                        } else {
                            return Single.error(new Exception("服务器返回error : " + tHttpResponse.getCount()));
                        }
                    }
                });
            }
        };
    }

    public static <T> SingleTransformer<T, T> getList() {   //compose判断结果
        return new SingleTransformer<T, T>() {
            @Override
            public Single<T> apply(Single<T> upstream) {
                return upstream.flatMap(new Function<T, Single<T>>() {
                    @Override
                    public Single<T> apply(T tHttpResponse) {
                        return createSingle(tHttpResponse);
                    }
                });
            }
        };
    }
//
//    public static <T> FlowableTransformer<ApiResponse<T>, T> fetchResults() {   //compose判断结果
//        return new FlowableTransformer<ApiResponse<T>, T>() {
//            @Override
//            public Flowable<T> apply(Flowable<ApiResponse<T>> httpResponseFlowable) {
//                return httpResponseFlowable.flatMap(new Function<ApiResponse<T>, Flowable<T>>() {
//                    @Override
//                    public Flowable<T> apply(ApiResponse<T> tHttpResponse) {
//                        if(tHttpResponse.getCount() > 0) {
//                            return createData(tHttpResponse.getResults());
//                        } else {
//                            return Flowable.error(new Exception("服务器返回error"));
//                        }
//                    }
//                });
//            }
//        };
//    }


    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    public static <T> Observable<T> createObservable(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }

        });
    }

    public static <T> Single<T> createSingle(final T t) {
        return Single.create(new SingleOnSubscribe<T>() {
            @Override
            public void subscribe(SingleEmitter<T> emitter) throws Exception {
                try {
                    emitter.onSuccess(t);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }

        });
    }

}
