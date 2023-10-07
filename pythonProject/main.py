from flask import Flask, render_template, request
import deezer

app = Flask(__name__)
# parses through the data using the deezer wrapper

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        query = request.form['query']
        search_type = request.form['search_type']
        client = deezer.Client()
        if search_type == 'album':
            album_results = client.search_albums(query)
            album_name = album_results[0].title
            artist_name = album_results[0].artist.name
            tracks = [track.title for track in album_results[0].tracks]
            return render_template('album.html', album_name=album_name, artist_name=artist_name, tracks=tracks)
        elif search_type == 'song':
            song_results = client.search(query)
            song_title = song_results[0].title
            album_title = song_results[0].album.title
            artist_name = song_results[0].artist.name
            release_date = song_results[0].album.release_date
            return render_template('song.html', song_title=song_title, album_title=album_title, artist_name=artist_name,
                                   release_date=release_date)
        elif search_type == 'artist':
            artist_results = client.search_artists(query)
            artist_id = artist_results[0].id
            artist_name = artist_results[0].name
            albums_results = client.get_artist(artist_id).get_albums()
            albums = [album.title for album in albums_results]
            return render_template('artist.html', albums=albums, artist_name=artist_name)
    return render_template('index.html')


if __name__ == '__main__':
    app.run(debug=True)
