import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Film} from '../dtos/programm';

@Injectable({
  providedIn: 'root'
})
export class ProgrammService {

  private programmUrl: string = '/api/programm';

  constructor(private http: HttpClient) {

  }

  public holeProgramm(datum: string): Observable<Film[]> {
    return this.http.get<Film[]>(this.programmUrl, {params: {datum: datum}})
  }

}
