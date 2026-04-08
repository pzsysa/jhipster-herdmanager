import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISeed, Seed } from '../seed.model';
import { CropType } from '../crop-type.model';
import { RainfallPattern } from '../rainfall-pattern.model';

import { SeedService } from './seed.service';

describe('Seed Service', () => {
  let service: SeedService;
  let httpMock: HttpTestingController;
  let elemDefault: ISeed;
  let expectedResult: ISeed | ISeed[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SeedService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      name: 'AAAA',
      cropType: CropType.BARLEY,
      region: 'AAAA',
      rainfallPattern: RainfallPattern.WINTER,
      waterRequirementMmToEstablish: 0,
      notes: 'AAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Seed', () => {
      const returnedFromService = Object.assign({ id: 0 }, elemDefault);
      const expected = Object.assign({}, returnedFromService);

      service.create(new Seed()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Seed', () => {
      const returnedFromService = Object.assign({ name: 'BBBB' }, elemDefault);
      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should delete a Seed', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    it('should return a list of Seeds', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    describe('addSeedToCollectionIfMissing', () => {
      it('should add a Seed to an empty array', () => {
        const seed: ISeed = { id: 123 };
        expectedResult = service.addSeedToCollectionIfMissing([], seed);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(seed);
      });

      it('should not add a Seed to an array that contains it', () => {
        const seed: ISeed = { id: 123 };
        const seedCollection: ISeed[] = [
          {
            ...seed,
          },
          { id: 456 },
        ];
        expectedResult = service.addSeedToCollectionIfMissing(seedCollection, seed);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Seed to an array that doesn't contain it", () => {
        const seed: ISeed = { id: 123 };
        const seedCollection: ISeed[] = [{ id: 456 }];
        expectedResult = service.addSeedToCollectionIfMissing(seedCollection, seed);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(seed);
      });

      it('should add only unique Seeds to an array', () => {
        const seedArray: ISeed[] = [{ id: 123 }, { id: 456 }, { id: 27699 }];
        const seedCollection: ISeed[] = [{ id: 456 }];
        expectedResult = service.addSeedToCollectionIfMissing(seedCollection, ...seedArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const seed: ISeed = { id: 123 };
        const seed2: ISeed = { id: 456 };
        expectedResult = service.addSeedToCollectionIfMissing([], seed, seed2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(seed);
        expect(expectedResult).toContain(seed2);
      });

      it('should accept null and undefined values', () => {
        const seed: ISeed = { id: 123 };
        expectedResult = service.addSeedToCollectionIfMissing([], null, seed, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(seed);
      });

      it('should return initial array if no Seed is added', () => {
        const seedCollection: ISeed[] = [{ id: 456 }];
        expectedResult = service.addSeedToCollectionIfMissing(seedCollection, null, undefined);
        expect(expectedResult).toEqual(seedCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
