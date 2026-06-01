import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Film} from '../dtos/programm';

@Injectable({
  providedIn: 'root'
})
export class ProgrammService {
  private http = inject(HttpClient);

  private programmUrl: string = '/api/programm';

  public holeProgramm(datum: string): Observable<Film[]> {
    return this.http.get<Film[]>(this.programmUrl, {params: {datum: datum}})
  }

}
