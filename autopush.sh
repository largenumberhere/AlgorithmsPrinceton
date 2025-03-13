#!/bin/bash
echo "Current Date and Time: $(date)"
git add ./**
git commit -m "Automated commit $(date)"
git push

