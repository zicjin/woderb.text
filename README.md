use local global gradle

# gradle docker
https://hub.docker.com/_/gradle/
``` powershell
docker run --rm --name gradle_dev `
    -v c:/work/woderb.text:/work/project `
    -w /work/project `
    -v c:/work/hanlp_data:/work/hanlp_data `
    gradle:4.1.0-jdk8-alpine gradle `
    run -PmainClass="Beam.WordCountByMongo" -PmainArgs="['--runner=SparkRunner']"

docker run --rm --name gradle_dev \
    -v ~/work/woderb.text:/work/project \
    -w /work/project \
    -v ~/work/hanlp_data:/work/hanlp_data \
    gradle:4.1.0-jdk8-alpine gradle \
    run -PmainClass="Beam.WordCountByMongo" -PmainArgs="['--runner=SparkRunner']"
```