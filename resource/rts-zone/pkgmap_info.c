
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <strings.h>

void syntax(char *progname)
{
    fprintf(stderr, "syntax: %s filename\n", progname);
}


int main(int argc, char *argv[])
{
struct stat statbuf;
char *sum_cmd = "/usr/bin/sum ";
FILE *fptr;
char cmd[BUFSIZ];
char buf[BUFSIZ];
int sum;

    if (argc == 1)
    {
        fprintf(stderr, "No file specified\n");
        syntax(argv[0]);
        exit(1);
    }

    if (stat(argv[1], &statbuf) != 0)
    {
        perror(argv[0]);
        syntax(argv[0]);
        exit(errno);
    }

    strcpy(cmd, sum_cmd);
    strlcat(cmd, argv[1], BUFSIZ);
    if ((fptr = popen(cmd, "r")) != NULL)
    {
        fgets(buf, BUFSIZ, fptr);
        (void) pclose(fptr);
        sscanf(buf, "%d", &sum);
    }

    printf("%s %d %d %d\n", argv[1], statbuf.st_size, sum, statbuf.st_atime);
}
