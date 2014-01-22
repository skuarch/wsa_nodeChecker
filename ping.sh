#!/bin/bash
ping -c 2 -t 60 $1 > /dev/null && echo "true" || echo "false"