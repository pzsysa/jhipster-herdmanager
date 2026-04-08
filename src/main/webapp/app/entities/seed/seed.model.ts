import { CropType } from './crop-type.model';
import { RainfallPattern } from './rainfall-pattern.model';

export interface ISeed {
  id?: number;
  name?: string;
  cropType?: CropType;
  region?: string;
  rainfallPattern?: RainfallPattern;
  waterRequirementMmToEstablish?: number;
  notes?: string | null;
}

export type NewSeed = Omit<ISeed, 'id'> & { id: null };

export class Seed implements ISeed {
  constructor(
    public id?: number,
    public name?: string,
    public cropType?: CropType,
    public region?: string,
    public rainfallPattern?: RainfallPattern,
    public waterRequirementMmToEstablish?: number,
    public notes?: string | null
  ) {}
}

export function getSeedIdentifier(seed: ISeed): number | undefined {
  return seed.id;
}
