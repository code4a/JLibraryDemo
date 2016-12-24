/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.code4a.jlibrarydemo.login;

import com.code4a.jlibrary.base.presenter.BasePresenter;

public abstract class LoginPresenter extends BasePresenter<LoginView> {

    protected LoginPresenter(LoginView view) {
        super(view);
    }

    public abstract void validateCredentials(String username, String password);

    public abstract void onDestroy();
}
