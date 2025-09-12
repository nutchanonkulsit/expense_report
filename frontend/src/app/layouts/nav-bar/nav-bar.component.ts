import { Component, ElementRef, HostListener, ViewChild } from '@angular/core';

@Component({
  selector: 'app-nav-bar',
  standalone: false,
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.css',
})
export class NavBarComponent {
  @ViewChild('header') headerRef!: ElementRef;
  headerHeight: number = 0;

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.updateHeaderHeight();
    });
  }

  @HostListener('window:resize')
  onResize() {
    this.updateHeaderHeight();
  }

  updateHeaderHeight() {
    if (this.headerRef) {
      this.headerHeight = this.headerRef.nativeElement.offsetHeight ;
    }
  }
}
