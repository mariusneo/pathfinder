/**
 * Copyright 2012 ArcBees Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package com.mg.search.client.application.map;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.mg.search.client.application.ApplicationPresenter;
import com.mg.search.client.place.NameTokens;

public class CreateMapPresenter extends Presenter<CreateMapPresenter.MyView, CreateMapPresenter.MyProxy>
        implements CreateMapUiHandlers {
    public interface MyView extends View, HasUiHandlers<CreateMapUiHandlers> {
        int getLines();

        int getColumns();
    }

    @ProxyStandard
    @NameToken(NameTokens.CREATE_MAP)
    public interface MyProxy extends ProxyPlace<CreateMapPresenter> {
    }

    private PlaceManager placeManager;

    @Inject
    public CreateMapPresenter(EventBus eventBus, MyView view, MyProxy proxy, final PlaceManager placeManager) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_SetMainContent);
        this.placeManager = placeManager;
        
        getView().setUiHandlers(this);
    }

    public void onCreateMapButtonClick() {
        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.EDIT_MAP)
                .with(EditMapPresenter.PARAMETER_COLUMNS, Integer.toString(getView().getColumns()))
                .with(EditMapPresenter.PARAMETER_LINES, Integer.toString(getView().getLines())).build());
    }

    public void onCloseButtonClick() {
        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.HOME).build());
    }

}