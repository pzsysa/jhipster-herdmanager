import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISeed, Seed } from '../seed.model';
import { SeedService } from '../service/seed.service';
import { CropType } from '../crop-type.model';
import { RainfallPattern } from '../rainfall-pattern.model';

const initialSeed: ISeed = new Seed();

@Component({
  selector: 'jhi-seed-update',
  templateUrl: './seed-update.component.html',
})
export class SeedUpdateComponent implements OnInit {
  isSaving = false;
  cropTypeValues = Object.keys(CropType);
  rainfallPatternValues = Object.keys(RainfallPattern);

  editForm = new FormGroup({
    id: new FormControl(initialSeed.id),
    name: new FormControl(initialSeed.name, {
      validators: [Validators.required, Validators.maxLength(100)],
    }),
    cropType: new FormControl(initialSeed.cropType, {
      validators: [Validators.required],
    }),
    region: new FormControl(initialSeed.region, {
      validators: [Validators.required, Validators.maxLength(100)],
    }),
    rainfallPattern: new FormControl(initialSeed.rainfallPattern, {
      validators: [Validators.required],
    }),
    waterRequirementMmToEstablish: new FormControl(initialSeed.waterRequirementMmToEstablish, {
      validators: [Validators.required, Validators.min(0)],
    }),
    notes: new FormControl(initialSeed.notes, {
      validators: [Validators.maxLength(500)],
    }),
  });

  constructor(protected seedService: SeedService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seed }) => {
      if (seed) {
        this.editForm.patchValue(seed);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const seed = this.editForm.getRawValue() as ISeed;
    if (seed.id !== null && seed.id !== undefined) {
      this.subscribeToSaveResponse(this.seedService.update(seed));
    } else {
      this.subscribeToSaveResponse(this.seedService.create(seed));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeed>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }
}
