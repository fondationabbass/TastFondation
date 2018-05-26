import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Candidature } from './candidature.model';
import { CandidaturePopupService } from './candidature-popup.service';
import { CandidatureService } from './candidature.service';

@Component({
    selector: 'jhi-candidature-dialog',
    templateUrl: './candidature-dialog.component.html'
})
export class CandidatureDialogComponent implements OnInit {

    candidature: Candidature;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private candidatureService: CandidatureService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.candidature.id !== undefined) {
            this.subscribeToSaveResponse(
                this.candidatureService.update(this.candidature));
        } else {
            this.subscribeToSaveResponse(
                this.candidatureService.create(this.candidature));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Candidature>>) {
        result.subscribe((res: HttpResponse<Candidature>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Candidature) {
        this.eventManager.broadcast({ name: 'candidatureListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-candidature-popup',
    template: ''
})
export class CandidaturePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private candidaturePopupService: CandidaturePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.candidaturePopupService
                    .open(CandidatureDialogComponent as Component, params['id']);
            } else {
                this.candidaturePopupService
                    .open(CandidatureDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
