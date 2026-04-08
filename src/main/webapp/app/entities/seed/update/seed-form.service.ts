import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISeed, NewSeed } from '../seed.model';

type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

type SeedFormGroupInput = ISeed | PartialWithRequiredKeyOf<NewSeed>;

@Injectable({ providedIn: 'root' })
export class SeedFormService {
  createSeedFormGroup(seed: SeedFormGroupInput = { id: null }): FormGroup {
    return new FormGroup({
      id: new FormControl(
        { value: seed.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(seed.name ?? null, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      cropType: new FormControl(seed.cropType ?? null, {
        validators: [Validators.required],
      }),
      region: new FormControl(seed.region ?? null, {
        validators: [Validators.required, Validators.maxLength(100)],
      }),
      rainfallPattern: new FormControl(seed.rainfallPattern ?? null, {
        validators: [Validators.required],
      }),
      waterRequirementMmToEstablish: new FormControl(seed.waterRequirementMmToEstablish ?? null, {
        validators: [Validators.required, Validators.min(0)],
      }),
      notes: new FormControl(seed.notes ?? null, {
        validators: [Validators.maxLength(500)],
      }),
    });
  }

  getSeed(form: FormGroup): ISeed | NewSeed {
    return form.getRawValue() as ISeed | NewSeed;
  }

  resetForm(form: FormGroup, seed: SeedFormGroupInput): void {
    const seedRawValue = { ...seed };
    form.reset({
      ...seedRawValue,
      id: { value: seedRawValue.id, disabled: true },
    } as any);
  }
}
