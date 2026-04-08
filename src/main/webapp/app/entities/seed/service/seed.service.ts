import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISeed, getSeedIdentifier } from '../seed.model';

export type EntityResponseType = HttpResponse<ISeed>;
export type EntityArrayResponseType = HttpResponse<ISeed[]>;

@Injectable({ providedIn: 'root' })
export class SeedService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/seeds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(seed: ISeed): Observable<EntityResponseType> {
    return this.http.post<ISeed>(this.resourceUrl, seed, { observe: 'response' });
  }

  update(seed: ISeed): Observable<EntityResponseType> {
    return this.http.put<ISeed>(`${this.resourceUrl}/${getSeedIdentifier(seed) as number}`, seed, { observe: 'response' });
  }

  partialUpdate(seed: ISeed): Observable<EntityResponseType> {
    return this.http.patch<ISeed>(`${this.resourceUrl}/${getSeedIdentifier(seed) as number}`, seed, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISeed>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISeed[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSeedToCollectionIfMissing(seedCollection: ISeed[], ...seedsToCheck: (ISeed | null | undefined)[]): ISeed[] {
    const seeds: ISeed[] = seedsToCheck.filter(isPresent) as ISeed[];
    if (seeds.length > 0) {
      const seedCollectionIdentifiers = seedCollection.map(seedItem => getSeedIdentifier(seedItem)!);
      const seedsToAdd = seeds.filter(seedItem => {
        const seedIdentifier = getSeedIdentifier(seedItem);
        if (seedIdentifier == null || seedCollectionIdentifiers.includes(seedIdentifier)) {
          return false;
        }
        seedCollectionIdentifiers.push(seedIdentifier);
        return true;
      });
      return [...seedsToAdd, ...seedCollection];
    }
    return seedCollection;
  }
}
