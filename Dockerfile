FROM ubuntu:latest
LABEL authors="hanli"

ENTRYPOINT ["top", "-b"]