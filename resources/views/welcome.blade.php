<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Smk Harvard Server</title>
</head>
<body>
    @foreach($mahasiswa as $mhs)
    {{$mhs->nama}}
    @endforeach
</body>
</html>